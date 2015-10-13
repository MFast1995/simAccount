/**

 * 
 */
package withdrawTest;
import simpleAccount.model.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import simpleAccount.model.CalculatorModel;
import simpleAccount.model.userAccnt;

/**
 * @author giovanniyancey
 *
 */
public class wATest {
	ArrayList <userAccnt> group;
	CalculatorModel CM;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		CM = new CalculatorModel();
		group = new ArrayList<userAccnt>();
		userAccnt acc = new userAccnt("Test", "Case", 001, 1000);	//creating a test user to run operations on
		group.add(1, acc);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		group.clear();
	}


	@Test
	public void testAA() {	
		assertTrue(group.size() == 1);		
		//fail("was not supposed to get here");
	}
	@Test
	public void testWA() {	
		CM.usdWithdraw(group, 1, 100);
		assertTrue(group.get(1).getAmount() == 900);
		//fail("was not supposed to get here");
	}
	@Test
	public void testDA() {	
		CM.usdDeposit(group, 1, 100);
		assertTrue(group.get(1).getAmount() == 1000);
		//fail("was not supposed to get here");
	}
	

}
