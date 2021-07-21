package nts.uk.ctx.at.record.dom.workmanagement.workinitselectset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public class TaskInitialSelHistHelper {
	
	public static TaskInitialSelHist getTaskInitialSelHistDefault(){
		TaskInitialSel item1 = new TaskInitialSel("000000000001", new DatePeriod( GeneralDate.ymd(2021, 06, 01),  GeneralDate.ymd(2021, 06, 30)), 
				new TaskItem(
						Optional.of(new TaskCode("TaskCode1") ),
						Optional.of(new TaskCode("TaskCode2") ),
						Optional.of(new TaskCode("TaskCode3") ),
						Optional.of(new TaskCode("TaskCode4") ),
						Optional.of(new TaskCode("TaskCode5") )
						));
		TaskInitialSel item2 = new TaskInitialSel("000000000001", new DatePeriod( GeneralDate.ymd(2021, 07, 01),  GeneralDate.ymd(2021, 07, 31)), 
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
		TaskInitialSelHist data = new TaskInitialSelHist(
				"000000000001",
				lst);
		return data;
	}
	
	
	public static TaskInitialSel getTaskInitialSelDefault(){
		TaskInitialSel item = new TaskInitialSel("000000000001", new DatePeriod( GeneralDate.ymd(2021, 05, 01),  GeneralDate.ymd(2021, 05, 30)), 
				new TaskItem(
						Optional.of(new TaskCode("TaskCode1") ),
						Optional.of(new TaskCode("TaskCode2") ),
						Optional.of(new TaskCode("TaskCode3") ),
						Optional.of(new TaskCode("TaskCode4") ),
						Optional.of(new TaskCode("TaskCode5") )
						));
		return item;
	}
	
}
