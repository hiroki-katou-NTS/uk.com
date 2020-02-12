package nts.uk.ctx.at.shared.dom.workrule;

import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ColorCodeChar6;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.NameShiftMater;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterDisInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMater;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaterCode;

public class ShiftMaterInstanceHelper {
	public static ShiftMater getShiftMaterEmpty() {
		return new ShiftMater("companyId", new ShiftMaterCode("smc"), 
				new ShiftMasterDisInfor(new NameShiftMater("name"),new ColorCodeChar6("color"), null), 
				"workTypeCode", "workTimeCode");
	}
	
	public static ShiftMater getShiftMater(String companyId, String shiftMaterCode, ShiftMasterDisInfor displayInfor,
			String workTypeCode, String workTimeCode) {
		return new ShiftMater(companyId, new ShiftMaterCode("smc"), displayInfor, workTypeCode, workTimeCode);
	}

}
