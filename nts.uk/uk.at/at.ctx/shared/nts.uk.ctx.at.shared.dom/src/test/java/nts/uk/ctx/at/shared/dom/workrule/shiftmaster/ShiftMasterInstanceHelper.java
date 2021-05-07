package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.WorkInformation;

public class ShiftMasterInstanceHelper {
	public static ShiftMaster getShiftMaterEmpty() {
		return new ShiftMaster("companyId", new ShiftMasterCode("smc"),
				new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"),new ColorCodeChar6("color"), Optional.empty()),
				"workTypeCode", "workTimeCode", new ShiftMasterImportCode("importCode"));
	}

	public static ShiftMaster getShiftMater(String companyId, String shiftMaterCode, ShiftMasterDisInfor displayInfor,
			String workTypeCode, String workTimeCode) {
		return new ShiftMaster(companyId, new ShiftMasterCode(shiftMaterCode), displayInfor, workTypeCode, workTimeCode, new ShiftMasterImportCode("importCode"));
	}

	public static ShiftMasterDisInfor getShiftMasterDisInforEmpty() {
		return new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"),new ColorCodeChar6("color"), Optional.empty());
	}

	public static boolean checkExist(boolean param){
		return param;
	}

	public static ShiftMaster getShiftMaterWorkTimeIsNull() {
		return new ShiftMaster("companyId", new ShiftMasterCode("smc"),
				new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"),new ColorCodeChar6("color"), Optional.empty()),
				"workTypeCode", null, new ShiftMasterImportCode("importCode"));
	}

	public static WorkInformation getWorkInformationWorkTimeIsNull() {
		return new WorkInformation( "workTypeCode", null);
	}

	public static ShiftMaster createShiftMasterByImportCode(ShiftMasterImportCode importCode) {
		return new ShiftMaster("companyId", new ShiftMasterCode("smc"),
				new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"),new ColorCodeChar6("color"), Optional.empty()),
				"workTypeCode", "workTimeCode", importCode);
	}


}
