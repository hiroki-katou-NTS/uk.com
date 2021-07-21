package nts.uk.ctx.at.record.dom.workmanagement.workinitselectset;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class TaskInitialSelHistTest {
	
	@Test
	public void getters() {
		TaskInitialSelHist data = TaskInitialSelHistHelper.getTaskInitialSelHistDefault();
		NtsAssert.invokeGetters(data);
	}
	
	/**
	 * Test func addHistory
	 * case1
	 */
	@Test
	public void testaddHistory1() {
		TaskInitialSelHist taskInitialSelHist = TaskInitialSelHistHelper.getTaskInitialSelHistDefault();
		TaskInitialSel param = TaskInitialSelHistHelper.getTaskInitialSelDefault();
		taskInitialSelHist.addHistory(param);
		
		// case latestHist isPresent() = true
		
		System.out.println(taskInitialSelHist);
		
		
	}
}
