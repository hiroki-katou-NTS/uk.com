package nts.uk.ctx.at.record.dom.monthly.mergetable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;

@Getter
@Setter
public class RemainMerge {
	MonthMergeKey monthMergeKey;
	AnnLeaRemNumEachMonth annLeaRemNumEachMonth;
	RsvLeaRemNumEachMonth rsvLeaRemNumEachMonth;
	SpecialHolidayRemainDataMerge specialHolidayRemainDataMerge;
	MonthlyDayoffRemainData monthlyDayoffRemainData;
	AbsenceLeaveRemainData absenceLeaveRemainData;

	/**  KRCDT_MON_CHILD_HD_REMAIN */
	//TODO code have not domain
	/** * KRCDT_MON_CARE_HD_REMAIN */
	//TODO code have not Domain
}
