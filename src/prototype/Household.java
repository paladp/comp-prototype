/**
 * 
 */
package prototype;





import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.graph.Network;
import repast.simphony.util.ContextUtils;

/**
 * @author patrick
 *
 */
public class Household {

	// Class variables that hold the context the agent is in, as well as the projections
	private ContinuousSpace<Object> space;
	private Context context;
	private bank myBank;
	
	// Class variables that represent wealth...
	private int wealth;
	private balancesheet ledger = new balancesheet(0,0);
	
	// Constructor that assigns a projection and starting wealth 
	public Household (ContinuousSpace<Object> space, int startingWealth) {
		this.space = space;
		this.wealth = startingWealth;
		this.ledger.increaseAssets(startingWealth);
	}
	
	// Method that adds a random amount of wealth to the agent
	@ScheduledMethod(start = 1, interval =1, priority = 5)
	public void genWealth() {
		this.ledger.increaseAssets(RandomHelper.nextIntFromTo(1, 5));
		System.out.println("Adding one more wealth to " + this + ". Current wealth is " + this.ledger.getAssets() );
	}
	
	// Method that saves a reference to the context, should be called as soon as the context has been build in
	// prototypeBuilder.java
	@ScheduledMethod(start=1, priority = 6)
	public void getContext() {
		this.context = ContextUtils.getContext(this);

	}
	
	// Access method
	public int getWealth() {
		return this.ledger.getAssets();
	}
	
	// Method that builds the consumption network and then consumes, should be split into two 
	// different methods
	@ScheduledMethod(start=1, interval=1, priority = 2)
	public void Consume() {
		Iterable<Object> randomConsumptionFirms = this.context.getRandomObjects(ConsumptionFirm.class, 2);
		Network<Object> consumptionNetwork = (Network<Object>)this.context.getProjection("consumption network");
		List<ConsumptionFirm> tradingPartners = new ArrayList<ConsumptionFirm>();
		
		for (Object currentPartner : randomConsumptionFirms) {
			System.out.println("Currently creating edge from " + this + "to " + currentPartner);
			consumptionNetwork.addEdge(this, currentPartner);
			tradingPartners.add((ConsumptionFirm)currentPartner);
		}
		
		tradingPartners.sort((p1, p2) -> Integer.compare(p1.getPrice(), p2.getPrice()));
		
		if(tradingPartners.get(0).canSellGoods() == true) {
			transaction currentTransaction = new transaction (tradingPartners.get(0).getPrice(), tradingPartners.get(0), this);
			this.parseTransaction(currentTransaction);
			tradingPartners.get(0).sellGoods(1, currentTransaction);
			System.out.println(this + " bought one good from " + tradingPartners.get(0));
			System.out.println(this + "now has " + this.ledger.getAssets() + " money.");
			/*
			this.wealth -= tradingPartners.get(0).getPrice();
			System.out.println(this + " bought one good from " + tradingPartners.get(0));
			System.out.println(this + "now has " + this.wealth + " money.");
			*/
		}	
	}
	
	@ScheduledMethod(start =1, interval=1, priority =3)
	public void assignBank() {
		Iterable<bank> randomBankIterable = this.context.getRandomObjects(bank.class, 1);
		Network<Object> bankNetwork = (Network<Object>)this.context.getProjection("bank network");
		Iterator randomBankList = randomBankIterable.iterator();
		this.myBank = (bank) randomBankList.next();
		bankNetwork.addEdge(this, this.myBank);
		
		this.myBank.createAccount(this, this.ledger.getAssets());
		
	}
	
	public void parseTransaction(transaction transaction_args) {
		if (transaction_args.getBuyer() == this) {
			this.ledger.decreaseAssets(transaction_args.getAmount());
		}
		else {
			System.out.println("Something bad happened");
		}
		
		this.myBank.parseTransaction(transaction_args);
		
		
	}
	
	
}
