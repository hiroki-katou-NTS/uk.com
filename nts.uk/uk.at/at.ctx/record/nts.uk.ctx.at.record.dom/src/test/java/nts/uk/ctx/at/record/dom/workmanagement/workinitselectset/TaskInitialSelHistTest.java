package nts.uk.ctx.at.record.dom.workmanagement.workinitselectset;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public class TaskInitialSelHistTest {
	
	@Test
	public void getters() {
		TaskInitialSelHist data = TaskInitialSelHistHelper.getTaskInitialSelHistDefault();
		NtsAssert.invokeGetters(data);
	}	
	
	/**
	 * 最新履歴の履歴項目() is empty
	 * 
	 */
	@Test
	public void testaddHistory1() {
		
		List<TaskInitialSel> lst = new ArrayList<>();
		TaskInitialSelHist taskInitialSelHist = new TaskInitialSelHist(
				"000000000001",
				lst);
		TaskInitialSel param = new TaskInitialSel("000000000001", new DatePeriod( GeneralDate.ymd(2021, 7,15),  GeneralDate.ymd(9999, 12,31)), 
				new TaskItem(
						Optional.of(new TaskCode("TaskCode1") ),
						Optional.of(new TaskCode("TaskCode2") ),
						Optional.of(new TaskCode("TaskCode3") ),
						Optional.of(new TaskCode("TaskCode4") ),
						Optional.of(new TaskCode("TaskCode5") )
						));
		taskInitialSelHist.addHistory(param);
		assertThat(taskInitialSelHist.getLstHistory().get(0)).isEqualToComparingFieldByField(param);
	}
	
	/**
	 * 最新履歴の履歴項目() not empty
	 */
	@Test
	public void testaddHistory2() {
		
		TaskInitialSel item1 = new TaskInitialSel("000000000001", new DatePeriod( GeneralDate.ymd(2021, 06, 01),  GeneralDate.ymd(2021, 06, 30)), 
				new TaskItem(
						Optional.of(new TaskCode("TaskCode1") ),
						Optional.of(new TaskCode("TaskCode2") ),
						Optional.of(new TaskCode("TaskCode3") ),
						Optional.of(new TaskCode("TaskCode4") ),
						Optional.of(new TaskCode("TaskCode5") )
						));
		TaskInitialSel item2 = new TaskInitialSel("000000000001", new DatePeriod( GeneralDate.ymd(2021, 07, 01),  GeneralDate.ymd(9999, 12, 31)), 
				new TaskItem(
						Optional.of(new TaskCode("TaskCode1") ),
						Optional.of(new TaskCode("TaskCode2") ),
						Optional.of(new TaskCode("TaskCode3") ),
						Optional.of(new TaskCode("TaskCode4") ),
						Optional.of(new TaskCode("TaskCode5") )
						));
		List<TaskInitialSel> lst = new ArrayList<>();
		lst.add(item1);
		lst.add(item2);
		TaskInitialSelHist taskInitialSelHist = new TaskInitialSelHist(
				"000000000001",
				lst);
		TaskInitialSel param = new TaskInitialSel("000000000001", new DatePeriod( GeneralDate.ymd(2021, 7,15),  GeneralDate.ymd(9999, 12,31)), 
				new TaskItem(
						Optional.of(new TaskCode("TaskCode1") ),
						Optional.of(new TaskCode("TaskCode2") ),
						Optional.of(new TaskCode("TaskCode3") ),
						Optional.of(new TaskCode("TaskCode4") ),
						Optional.of(new TaskCode("TaskCode5") )
						));
		taskInitialSelHist.addHistory(param);
		assertThat( taskInitialSelHist.getLstHistory() )
		.extracting( 
				d -> d.getDatePeriod()
				)
		.containsExactly(
				 item1.getDatePeriod(),
				 param.getDatePeriod(),
				 new DatePeriod(GeneralDate.ymd(2021, 07, 01), GeneralDate.ymd(2021, 7,14)));
	}
	
	/*
	 * test Func deleteHistory
	 * case if $直前の履歴.isPresent = true
	 */
	@Test
	public void deleteHistory1() {
		TaskInitialSel item1 = new TaskInitialSel("000000000001", new DatePeriod( GeneralDate.ymd(2021, 06, 01),  GeneralDate.ymd(2021, 06, 30)), 
				new TaskItem(
						Optional.of(new TaskCode("TaskCode1") ),
						Optional.of(new TaskCode("TaskCode2") ),
						Optional.of(new TaskCode("TaskCode3") ),
						Optional.of(new TaskCode("TaskCode4") ),
						Optional.of(new TaskCode("TaskCode5") )
						));
		TaskInitialSel item2 = new TaskInitialSel("000000000001", new DatePeriod( GeneralDate.ymd(2021, 07, 01),  GeneralDate.ymd(9999, 12, 31)), 
				new TaskItem(
						Optional.of(new TaskCode("TaskCode1") ),
						Optional.of(new TaskCode("TaskCode2") ),
						Optional.of(new TaskCode("TaskCode3") ),
						Optional.of(new TaskCode("TaskCode4") ),
						Optional.of(new TaskCode("TaskCode5") )
						));
		List<TaskInitialSel> lst = new ArrayList<>();
		lst.add(item1);
		lst.add(item2);
		TaskInitialSelHist taskInitialSelHist = new TaskInitialSelHist(
				"000000000001",
				lst);
		TaskInitialSel param = new TaskInitialSel("000000000001", new 
				DatePeriod( GeneralDate.ymd(2021, 07, 01),  GeneralDate.ymd(9999, 12, 31)), 
				new TaskItem(
						Optional.of(new TaskCode("TaskCode1") ),
						Optional.of(new TaskCode("TaskCode2") ),
						Optional.of(new TaskCode("TaskCode3") ),
						Optional.of(new TaskCode("TaskCode4") ),
						Optional.of(new TaskCode("TaskCode5") )
						));
		
		taskInitialSelHist.deleteHistory(param);	
		List<TaskInitialSel> lstHistoryResult = taskInitialSelHist.getLstHistory();
		assertThat(lstHistoryResult.get(0).getDatePeriod().start()).isEqualTo(GeneralDate.ymd(2021, 06, 01));
		assertThat(lstHistoryResult.get(0).getDatePeriod().end()).isEqualTo(GeneralDate.ymd(9999, 12, 31));
		System.out.println(taskInitialSelHist);
	}
	
	/*
	 * test Func changeHistory
	 * case if 変更する履歴項目.期間 == 変更後の期間
	 */
	@Test
	public void changeHistory1() {
		TaskInitialSel item1 = new TaskInitialSel("000000000001", new DatePeriod( GeneralDate.ymd(2021, 06, 01),  GeneralDate.ymd(2021, 06, 30)), 
				new TaskItem(
						Optional.of(new TaskCode("TaskCode1")),
						Optional.of(new TaskCode("TaskCode2")),
						Optional.of(new TaskCode("TaskCode3")),
						Optional.of(new TaskCode("TaskCode4")),
						Optional.of(new TaskCode("TaskCode5"))
						));
		TaskInitialSel item2 = new TaskInitialSel("000000000001", new DatePeriod( GeneralDate.ymd(2021, 07, 01),  GeneralDate.ymd(9999, 12, 31)), 
				new TaskItem(
						Optional.of(new TaskCode("TaskCode1")),
						Optional.of(new TaskCode("TaskCode2")),
						Optional.of(new TaskCode("TaskCode3")),
						Optional.of(new TaskCode("TaskCode4")),
						Optional.of(new TaskCode("TaskCode5"))
						));
		List<TaskInitialSel> lst = new ArrayList<>();
		lst.add(item1);
		lst.add(item2);
		TaskInitialSelHist taskInitialSelHist = new TaskInitialSelHist(
				"000000000001",
				lst);			
		
		TaskInitialSel taskInitialSel = new TaskInitialSel("000000000001", new DatePeriod( GeneralDate.ymd(2021, 07, 01),  GeneralDate.ymd(9999, 12, 31)), 
				new TaskItem(
						Optional.of(new TaskCode("TaskCode1")),
						Optional.of(new TaskCode("TaskCode2")),
						Optional.of(new TaskCode("TaskCode3")),
						Optional.of(new TaskCode("TaskCode4")),
						Optional.of(new TaskCode("TaskCode5"))
						));
		DatePeriod period =  new DatePeriod( GeneralDate.ymd(2021, 07, 01),  GeneralDate.ymd(9999, 12, 31));
		TaskItem taskItem = new TaskItem(
				Optional.of(new TaskCode("TaskCode6")),
				Optional.of(new TaskCode("TaskCode7")),
				Optional.of(new TaskCode("TaskCode8")),
				Optional.of(new TaskCode("TaskCode9")),
				Optional.of(new TaskCode("TaskCode10"))
				);
		taskInitialSelHist.changeHistory(taskInitialSel, period, taskItem);
		assertThat(taskInitialSelHist.getLstHistory().get(1).getTaskItem()).isEqualTo(taskItem);
	}
	
	/*
	 * test Func changeHistory
	 * case if 変更する履歴項目.期間 != 変更後の期間
	 */
	@Test
	public void changeHistory2() {
		TaskInitialSel item1 = new TaskInitialSel("000000000001", new DatePeriod( GeneralDate.ymd(2021, 06, 01),  GeneralDate.ymd(2021, 06, 30)), 
				new TaskItem(
						Optional.of(new TaskCode("TaskCode1")),
						Optional.of(new TaskCode("TaskCode2")),
						Optional.of(new TaskCode("TaskCode3")),
						Optional.of(new TaskCode("TaskCode4")),
						Optional.of(new TaskCode("TaskCode5"))
						));
		TaskInitialSel item2 = new TaskInitialSel("000000000001", new DatePeriod( GeneralDate.ymd(2021, 07, 01),  GeneralDate.ymd(9999, 12, 31)), 
				new TaskItem(
						Optional.of(new TaskCode("TaskCode1")),
						Optional.of(new TaskCode("TaskCode2")),
						Optional.of(new TaskCode("TaskCode3")),
						Optional.of(new TaskCode("TaskCode4")),
						Optional.of(new TaskCode("TaskCode5"))
						));
		List<TaskInitialSel> lst = new ArrayList<>();
		lst.add(item1);
		lst.add(item2);
		TaskInitialSelHist taskInitialSelHist = new TaskInitialSelHist(
				"000000000001",
				lst);			
		
		TaskInitialSel taskInitialSel = new TaskInitialSel("000000000001", new DatePeriod( GeneralDate.ymd(2021, 8, 01),  GeneralDate.ymd(9999, 12, 31)), 
				new TaskItem(
						Optional.of(new TaskCode("TaskCode1")),
						Optional.of(new TaskCode("TaskCode2")),
						Optional.of(new TaskCode("TaskCode3")),
						Optional.of(new TaskCode("TaskCode4")),
						Optional.of(new TaskCode("TaskCode5"))
						));
		DatePeriod period =  new DatePeriod( GeneralDate.ymd(2021, 9, 01),  GeneralDate.ymd(9999, 12, 31));
		TaskItem taskItem = new TaskItem(
				Optional.of(new TaskCode("TaskCode6")),
				Optional.of(new TaskCode("TaskCode7")),
				Optional.of(new TaskCode("TaskCode8")),
				Optional.of(new TaskCode("TaskCode9")),
				Optional.of(new TaskCode("TaskCode10"))
				);
		taskInitialSelHist.changeHistory(taskInitialSel, period, taskItem);
		assertThat(taskInitialSelHist.getLstHistory().get(0).getDatePeriod()).isEqualTo(item1.getDatePeriod());
		assertThat(taskInitialSelHist.getLstHistory().get(1).getDatePeriod()).isEqualTo(item2.getDatePeriod());
	}
}
