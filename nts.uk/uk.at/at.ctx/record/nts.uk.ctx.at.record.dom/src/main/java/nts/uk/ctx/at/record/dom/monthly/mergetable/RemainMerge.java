package nts.uk.ctx.at.record.dom.monthly.mergetable;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.information.care.MonCareHdRemain;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.information.childnursing.MonChildHdRemain;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.absenceleave.AbsenceLeaveRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.dayoff.MonthlyDayoffRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;

/**
 * 残数系データ
 * @author shuichi_ishida
 */
@Getter
@Setter
public class RemainMerge {
	MonthMergeKey monthMergeKey;
	AnnLeaRemNumEachMonth annLeaRemNumEachMonth;
	RsvLeaRemNumEachMonth rsvLeaRemNumEachMonth;
	List<SpecialHolidayRemainData> specialHolidayRemainData = new ArrayList<>();
	MonthlyDayoffRemainData monthlyDayoffRemainData;
	AbsenceLeaveRemainData absenceLeaveRemainData;
	MonChildHdRemain monChildHdRemain;
	MonCareHdRemain monCareHdRemain;
	
	public RemainMerge(){
	}
	
	public boolean isEmpty(){
		if (this.monthMergeKey != null) return false;
		if (this.annLeaRemNumEachMonth != null) return false;
		if (this.rsvLeaRemNumEachMonth != null) return false;
		if (!this.specialHolidayRemainData.isEmpty()) return false;
		if (this.monthlyDayoffRemainData != null) return false;
		if (this.absenceLeaveRemainData != null) return false;
		if (this.monChildHdRemain != null) return false;
		if (this.monCareHdRemain != null) return false;
		return true;
	}
}
