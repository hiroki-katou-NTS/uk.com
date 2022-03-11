package nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem;

import java.util.ArrayList;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.ProductNumber;

public class OutsideOTBRDItemHelper {
	public static OutsideOTBRDItem createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo breakdownItemNo,
			UseClassification useClassification) {
		return new OutsideOTBRDItem(useClassification, breakdownItemNo, new BreakdownItemName("name"),
				ProductNumber.FOUR, new ArrayList<>(), new ArrayList<>());
	}
}
