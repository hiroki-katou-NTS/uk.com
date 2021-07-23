package nts.uk.ctx.at.record.dom.workmanagement.workinitselectset;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.calendar.period.DatePeriod;

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
	
	@Test
	public void deleteHistory1() {
		TaskInitialSelHist taskInitialSelHist = TaskInitialSelHistHelper.getTaskInitialSelHistDefault();
		TaskInitialSel param = TaskInitialSelHistHelper.getTaskInitialSelDefault();
		taskInitialSelHist.deleteHistory(param);	
		
		System.out.println(taskInitialSelHist);
		
		
	}
	
	@Test
	public void changeHistory1() {
		TaskInitialSelHist taskInitialSelHist = TaskInitialSelHistHelper.getTaskInitialSelHistDefault();
		TaskInitialSel taskInitialSel = TaskInitialSelHistHelper.getTaskInitialSelDefault();
		DatePeriod period =  TaskInitialSelHistHelper.getDatePeriodDefault();
		TaskItem taskItem = TaskInitialSelHistHelper.getTaskItemDefault();
		taskInitialSelHist.changeHistory(taskInitialSel, period, taskItem);
		
		
		System.out.println(taskInitialSelHist);
		
		
	}
}
