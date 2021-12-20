package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;

public class OvertimeHelper {
	public static Overtime createOvertimeByNoAndAtr(OvertimeNo no, UseClassification useClassification) {
		return new Overtime(false, useClassification, new OvertimeName("name"), new OvertimeValue(10), no);
	}
}
