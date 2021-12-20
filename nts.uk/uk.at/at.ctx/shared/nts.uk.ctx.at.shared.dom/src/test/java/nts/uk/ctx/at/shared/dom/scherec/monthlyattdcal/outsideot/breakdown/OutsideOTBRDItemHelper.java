package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown;

import java.util.ArrayList;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;

public class OutsideOTBRDItemHelper {
	public static OutsideOTBRDItem createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo breakdownItemNo,
			UseClassification useClassification) {
		return new OutsideOTBRDItem(useClassification, breakdownItemNo, new BreakdownItemName("name"),
				ProductNumber.FOUR, new ArrayList<>(), new ArrayList<>());
	}
}
