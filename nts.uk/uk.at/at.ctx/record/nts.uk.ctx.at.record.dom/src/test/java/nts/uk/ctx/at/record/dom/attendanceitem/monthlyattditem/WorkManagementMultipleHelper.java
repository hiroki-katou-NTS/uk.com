package nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem;

import nts.uk.ctx.at.shared.dom.workmanagementmultiple.UseATR;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.shr.com.license.option.OptionLicense;

public class WorkManagementMultipleHelper {
	public static OptionLicense optionLicense() {
		return new OptionLicense() {
		};
	}
	public static WorkManagementMultiple createWorkManagementMultiple() {
		return new WorkManagementMultiple("000000000006-0008", UseATR.use);
	}
	
	public static WorkManagementMultiple createWorkManagementMultiple_Use(UseATR atr) {
		return new WorkManagementMultiple("000000000006-0008", atr);
	}
	
	public static WorkManagementMultiple createWorkManagementMultiple_NotUse(UseATR atr) {
		return new WorkManagementMultiple("000000000006-0008", atr);
	}
}
