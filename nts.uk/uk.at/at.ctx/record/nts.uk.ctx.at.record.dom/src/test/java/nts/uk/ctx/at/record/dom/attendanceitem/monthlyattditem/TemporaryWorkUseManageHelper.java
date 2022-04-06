package nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;
import nts.uk.shr.com.license.option.OptionLicense;

public class TemporaryWorkUseManageHelper {
	public static OptionLicense optionLicense() {
		return new OptionLicense() {
		};
	}
	public static TemporaryWorkUseManage createTemporaryWorkUseManage() {
		return new TemporaryWorkUseManage(new CompanyId("000000000006-0008"), UseAtr.USE);
	}
	
	public static TemporaryWorkUseManage createTemporaryWorkUseManage_Use(UseAtr atr) {
		return new TemporaryWorkUseManage(new CompanyId("000000000006-0008"), atr);
	}
	
	public static TemporaryWorkUseManage createTemporaryWorkUseManage_NotUse(UseAtr atr) {
		return new TemporaryWorkUseManage(new CompanyId("000000000006-0008"), atr);
	}
}
