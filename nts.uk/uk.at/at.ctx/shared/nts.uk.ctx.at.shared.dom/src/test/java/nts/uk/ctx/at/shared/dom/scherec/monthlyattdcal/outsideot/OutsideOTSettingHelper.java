package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNote;

public class OutsideOTSettingHelper {
	public static OutsideOTSetting createOutsideOTSettingDefault(List<Overtime> overtimes,List<OutsideOTBRDItem> breakdownItems) {
		return new OutsideOTSetting("companyId", new OvertimeNote("note"), breakdownItems, overtimes);
	}
}
