package prototype;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.environment.RunState;
import repast.simphony.engine.schedule.Schedule;
import repast.simphony.space.continuous.ContinuousSpace;

public class HouseholdTest {

	public Context<Object> context;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Schedule schedule = new Schedule();
		RunEnvironment.init(schedule, null, null, true);
		context = new DefaultContext();
		prototypeBuilder builder = new prototypeBuilder();
		context = builder.build(context);
		RunState.init().setMasterContext(context);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenWealth() {
		Iterable<Object> randomHouseholdFirms = this.context.getRandomObjects(Household.class, 2);
		List<Household> households = new ArrayList<Household>();
		for (Object currentHouse : randomHouseholdFirms) {
			households.add((Household)currentHouse);
		}
		for (Household currentHouse : households) {
			assertEquals("correct", currentHouse.getWealth(), 100);
		}
		
	}

	@Test
	public void testGetWealth() {
	}

	@Test
	public void testConsume() {
	}

}
