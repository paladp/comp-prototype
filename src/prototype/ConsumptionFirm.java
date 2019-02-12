package prototype;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;

// Prototype class for the consumption firm agent
public class ConsumptionFirm {
	
	// Class variables related to the context and projection this agent is in
	private ContinuousSpace space;
	
	// General class variables 
	private int wealth;
	private int numOfGoods;
	private int price = RandomHelper.nextIntFromTo(2, 4);
	
	// Constructor
	public ConsumptionFirm(ContinuousSpace space, int startingWealth, int startingGoods) {
		this.wealth = startingWealth;
		this.numOfGoods = startingGoods;
		this.space = space;
	}
	
	// Generates a random number of goods
	@ScheduledMethod(start = 1, interval = 1, priority = 4)
	public void genGoods() {
		this.numOfGoods += RandomHelper.nextIntFromTo(1, 3);
	}
	
	// Access method
	public int getPrice () {
		return this.price;
	}
	
	// Method that other agents can call to buy from this agent
	public void sellGoods( int amount , transaction transaction_args) {
		if (transaction_args.getSeller() == this) {
			this.numOfGoods -= amount;
			this.wealth += transaction_args.getAmount();
		}
		else {
			System.out.println("Something went wrong in consumption firm");
		}
	}
	
	// Check method to see if this agent can sell goods
	public boolean canSellGoods() {
		if (this.numOfGoods != 0) {
			return true;
		}
		else return false;
	}

}
