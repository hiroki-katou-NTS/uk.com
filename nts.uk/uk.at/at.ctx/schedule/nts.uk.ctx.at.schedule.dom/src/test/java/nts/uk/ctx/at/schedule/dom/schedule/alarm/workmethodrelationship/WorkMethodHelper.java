package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeAbbreviationName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeMemo;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSymbolicName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

public class WorkMethodHelper {
	
	public static WorkInformation WORK_INFO_DUMMY =  new WorkInformation(new WorkTypeCode("001"), new WorkTimeCode("002"));
	
	/**
	 * 1日の勤務 != 1日
	 * DailyWork != ONEDAY 
	 * @return
	 */
	public static WorkType createWorkTypeNotOneDay() {
		return new WorkType(new WorkTypeCode("002"),
				new WorkTypeSymbolicName("symbName"), new WorkTypeName("name"), new WorkTypeAbbreviationName("abbName"),
				new WorkTypeMemo("memo"),
				new DailyWork(WorkTypeUnit.MonringAndAfternoon, WorkTypeClassification.ContinuousWork,
						WorkTypeClassification.Attendance, WorkTypeClassification.Attendance));
	}
	
	
	/**
	 * 1日の勤務  != 連続勤務
	 * DailyWork != CONTINUOUS
	 * @return
	 */
	
	public static WorkType createWorkTypeNotContinuous() {
		return new WorkType(new WorkTypeCode("002"),
				new WorkTypeSymbolicName("symbName"), new WorkTypeName("name"), new WorkTypeAbbreviationName("abbName"),
				new WorkTypeMemo("memo"),
				new DailyWork(WorkTypeUnit.MonringAndAfternoon, WorkTypeClassification.ContinuousWork,
						WorkTypeClassification.Attendance, WorkTypeClassification.Attendance));
	}
	
	/**
	 * 1日の勤務 != 1日 && 1日の勤務 != 連続勤務
	 * DailyWork != ONEDAY && DailyWork != CONTINUOUS
	 * @return
	 */
	public static WorkType createWorkTypeNotOneDayAndNotContinuous() {
		return new WorkType(new WorkTypeCode("002"),
				new WorkTypeSymbolicName("symbName"), new WorkTypeName("name"), new WorkTypeAbbreviationName("abbName"),
				new WorkTypeMemo("memo"),
				new DailyWork(WorkTypeUnit.MonringAndAfternoon, WorkTypeClassification.ContinuousWork,
						WorkTypeClassification.Attendance, WorkTypeClassification.Attendance));
	}
	
	/**
	 * 1日の勤務  = 1日 && 1日の勤務  = 連続勤務
	 * DailyWork == ONEDAY && DailyWork == CONTINUOUS
	 * @return
	 */
	public static WorkType createWorkTypeOneDayAndContinuous() {
		return new WorkType(new WorkTypeCode("002"),
				new WorkTypeSymbolicName("symbName"), new WorkTypeName("name"), new WorkTypeAbbreviationName("abbName"),
				new WorkTypeMemo("memo"),
				new DailyWork(WorkTypeUnit.OneDay, WorkTypeClassification.ContinuousWork,
						WorkTypeClassification.ContinuousWork, WorkTypeClassification.ContinuousWork));
	}
}
