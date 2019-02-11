/**
 * 
 */
package prototype;





import java.util.ArrayList;
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
	
	// Class variables that represent wealth...
	private int wealth;
	
	// Constructor that assigns a projection and starting wealth 
	public Household (ContinuousSpace<Object> space, int startingWealth) {
		this.space = space;
		this.wealth = startingWealth;
	}
	
	// Method that adds a random amount of wealth to the agent
	@ScheduledMethod(start = 1, interval =1, priority = 5)
	public void genWealth() {
		this.wealth += RandomHelper.nextIntFromTo(1, 5);
		System.out.println("Adding one more wealth to " + this + ". Current wealth is " + this.wealth );
	}
	
	// Method that saves a reference to the context, should be called as soon as the context has been build in
	// prototypeBuilder.java
	@ScheduledMethod(start=1, priority = 6)
	public void getContext() {
		this.context = ContextUtils.getContext(this);

	}
	
	// Access method
	public int getWealth() {
		return this.wealth;
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
			tradingPartners.get(0).sellGoods(1);
			this.wealth -= tradingPartners.get(0).getPrice();
			System.out.println(this + " bought one good from " + tradingPartners.get(0));
			System.out.println(this + "now has " + this.wealth + " money.");
		}
		
		
		
		
		
		
	}
	
	
}
