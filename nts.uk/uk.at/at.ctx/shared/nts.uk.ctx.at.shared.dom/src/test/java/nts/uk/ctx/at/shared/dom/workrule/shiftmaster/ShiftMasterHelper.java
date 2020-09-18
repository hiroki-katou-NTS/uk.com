package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.WorkInformation;

public class ShiftMasterHelper {
	public static List<ShiftMaster> getListShiftMasterByNumber(int number) {
		List<ShiftMaster> data = new ArrayList<>();
		for(int i =1;i<= number;i++) {
			String shiftMasterCode = "00"+i;
			String workTypeCode = "workTypeCode"+i;
			String workTimeCode = "workTimeCode"+i;
			ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"+i),new ColorCodeChar6("color"+i), null);
			ShiftMaster shiftMater = new ShiftMaster("companyId",new ShiftMasterCode(shiftMasterCode), displayInfor, workTypeCode,workTimeCode);
			data.add(shiftMater);
		}
		
		return data;
		
	}
	public static List<WorkInformation> getListWorkInformationByNumber(int number) {
		List<WorkInformation> data = new ArrayList<>();
		for(int i =1;i<= number;i++) {
			WorkInformation workInformation = new WorkInformation("00"+i, "00"+i);
			data.add(workInformation);
		}
		return data;
		
	}
	
    public static ShiftMaster createShiftMasterWithCode (String shiftMasterCode) {
        ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(
                new ShiftMasterName( shiftMasterCode + "-name"),
                new ColorCodeChar6(shiftMasterCode + "-color"),
                null);
        
        return new ShiftMaster("companyId",
                new ShiftMasterCode(shiftMasterCode), 
                displayInfor, 
                "1","1");

    }
}
