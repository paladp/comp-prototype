package prototype;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.graph.Network;
import repast.simphony.util.collections.IndexedIterable;

//If you see this the push from desktop worked

//If you see this the push from laptop worked

public class prototypeBuilder implements ContextBuilder<Object> {
	
	// Class variables. Currently will fall into two different categories
	// First category: Variables that store references to the master context or projections 
	// Second Category: Variables that store information for aggregate information 
	public Context<Object> context;
	public int totalHouseholdWealth;
	public 	Network<Object> consumptionNetwork;
	public ContinuousSpace<Object> space;
	
	// The main function of this class. This builds the context for the program
	@Override
	public Context build(Context<Object> context) {
		
		// Set the ID for the context, used in the xml file for the program
		context.setId("prototype");
		
		// The standard way of creating a Continuous Space projection, first create a factory 
		// After the factory is made, call the createContinuousSpace function and pass in these 
		// parameters: first the name, next the context to add it to, then the coordinates, then wrapping, then size
		ContinuousSpaceFactory  spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		
		space = spaceFactory.createContinuousSpace("space", context, 
										new RandomCartesianAdder<Object>(), 
										new repast.simphony.space.continuous.WrapAroundBorders(), 50,50 );
		
		
		// The standard way for creating a network, instead of a factory it's just a builder which takes in the 
		// name, context, and then a boolean for a directed or non directed graph. 
		NetworkBuilder<Object> consumptionBuilder = new NetworkBuilder<Object> ("consumption network", context, false);
		consumptionBuilder.buildNetwork();
		
		NetworkBuilder<Object> bankBuilder = new NetworkBuilder<Object> ("bank network", context, false);
		bankBuilder.buildNetwork();
		
		
		// Loop that adds agents to the context
		int householdCount = 5;
		for(int i = 0; i < householdCount; i++) {
			context.add(new Household(space, 100));
			context.add(new ConsumptionFirm(space, 100, 10));
			context.add(new bank());
		}
		
	
		// Saves the context and projections to the class variables so other functions can reference them 
		this.context = context;
		this.consumptionNetwork = (Network<Object>)this.context.getProjection("consumption network");
		
		// Use a RunEnvironment variable to get a reference to the simulations schedule, so we can 
		// manually add methods to the scheduler. There is a way to automatically do this using annotated 
		// methods but I have not found a way to make it work within the context builder class
		RunEnvironment currentEnv = RunEnvironment.getInstance();
		ISchedule schedule = currentEnv.getCurrentSchedule();
		
		// Create a ScheduleParameters variable to pass onto the schedule function. 
		// the syntax for this is similar to the annotated syntax
		ScheduleParameters params = ScheduleParameters.createRepeating(1,1,3);
		schedule.schedule(params, this, "sumUpWealth");
		
		// Once the context has been setup, return it to the GUI, past this point the context 
		// should not need to be changed, but the projections may be interacted with.
		return context;
	}
	
	// Basic function to explore a way to track global aggregates. Currently done through 
	// collecting all households in the context and calling the getWealth function
	// TODO: Remove the statement that clears the consumption network to a different function
	// that clears the required projections
	
	public void sumUpWealth() {
		
		totalHouseholdWealth = 0;
		
		// Retrieve all household agents in context
		IndexedIterable<Object> households = this.context.getObjects(Household.class); 
		
		
		for(Object currentHousehold : households) {
			totalHouseholdWealth += ((Household) currentHousehold).getWealth();
		}
		
		System.out.println("For this timestep total household wealth is: " + totalHouseholdWealth) ;
		
		this.consumptionNetwork.removeEdges();
		
		System.out.println("No more edges");
	}
	
	public ContinuousSpace<Object> getSpace () {
		return this.space;
	}
	
	
	
}
