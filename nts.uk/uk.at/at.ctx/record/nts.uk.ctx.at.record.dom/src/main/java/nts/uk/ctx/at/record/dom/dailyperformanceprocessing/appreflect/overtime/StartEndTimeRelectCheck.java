package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Getter
@Setter
@AllArgsConstructor
public class StartEndTimeRelectCheck {
	String employeeId;
	GeneralDate baseDate;
	Integer startTime1;
	Integer endTime1;
	Integer startTime2;
	Integer endTime2;
	String workTimeCode;
	String workTypeCode;
	/**
	 * 残業区分
	 */
	OverTimeRecordAtr overTimeAtr;
	
	

}
