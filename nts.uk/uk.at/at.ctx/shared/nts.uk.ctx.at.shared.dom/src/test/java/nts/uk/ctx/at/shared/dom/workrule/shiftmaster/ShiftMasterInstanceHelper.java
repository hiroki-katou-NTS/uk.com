package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ColorCodeChar6;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterName;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterDisInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;

public class ShiftMasterInstanceHelper {
	public static ShiftMaster getShiftMaterEmpty() {
		return new ShiftMaster("companyId", new ShiftMasterCode("smc"), 
				new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null), 
				"workTypeCode", "workTimeCode");
	}
	
	public static ShiftMaster getShiftMater(String companyId, String shiftMaterCode, ShiftMasterDisInfor displayInfor,
			String workTypeCode, String workTimeCode) {
		return new ShiftMaster(companyId, new ShiftMasterCode("smc"), displayInfor, workTypeCode, workTimeCode);
	}
	
	public static ShiftMasterDisInfor getShiftMasterDisInforEmpty() {
		return new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
	}
	
	public static boolean checkExist(boolean param){
		return param;
	}

}
