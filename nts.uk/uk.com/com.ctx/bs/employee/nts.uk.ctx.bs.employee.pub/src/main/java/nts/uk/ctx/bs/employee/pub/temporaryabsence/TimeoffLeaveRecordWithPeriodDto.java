package nts.uk.ctx.bs.employee.pub.temporaryabsence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
@RequiredArgsConstructor
public class TimeoffLeaveRecordWithPeriodDto {
	
	/** 期間**/
	private final DatePeriod datePeriod; 
	/** 履歴項目**/
	private final TempAbsenceHisItemDto TempAbsenceHisItem;

}
