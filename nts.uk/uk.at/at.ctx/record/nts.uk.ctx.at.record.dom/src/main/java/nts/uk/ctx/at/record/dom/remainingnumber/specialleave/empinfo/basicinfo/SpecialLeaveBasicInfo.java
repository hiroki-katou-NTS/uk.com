package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;

@Getter
public class SpecialLeaveBasicInfo {
	
	private String employeeId;
	
	private String specialLeaveCode;

	private UseAtr used;
	
	private SpecialLeaveAppSetting appSetting;
	
	private SpecialLeaveGrantSetting grantSetting;
	
}
