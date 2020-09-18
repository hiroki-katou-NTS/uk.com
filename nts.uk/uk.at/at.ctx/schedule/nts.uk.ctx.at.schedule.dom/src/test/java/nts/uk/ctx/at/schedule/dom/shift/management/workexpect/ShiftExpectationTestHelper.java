package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ColorCodeChar6;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.Remarks;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterDisInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterName;

public class ShiftExpectationTestHelper {
	
	public static ShiftMaster createShiftMasterWithCode(String shiftMasterCode) {
		return new ShiftMaster(
				shiftMasterCode + "-sid", 
    			new ShiftMasterCode(shiftMasterCode), 
    			new ShiftMasterDisInfor(
    					new ShiftMasterName(shiftMasterCode + "-name"), 
    					new ColorCodeChar6("000000"), 
    					new Remarks(shiftMasterCode + "-r")), 
    			"001", 
    			"001");
		
	}
	
	public static ShiftMaster createShiftMasterWithCodeName(String shiftMasterCode, String shiftMasterName) {
		return new ShiftMaster(
				shiftMasterCode + "-sid", 
    			new ShiftMasterCode(shiftMasterCode), 
    			new ShiftMasterDisInfor(
    					new ShiftMasterName(shiftMasterName), 
    					new ColorCodeChar6("000000"), 
    					new Remarks(shiftMasterCode + "-r")), 
    			"001", 
    			"001");
		
	}

}
