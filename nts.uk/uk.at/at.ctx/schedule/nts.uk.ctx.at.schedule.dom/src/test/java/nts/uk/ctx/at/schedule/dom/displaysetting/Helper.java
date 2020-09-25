package nts.uk.ctx.at.schedule.dom.displaysetting;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public class Helper {
	
	public static class GetDispByDateService {
		
		public static class TargetOrg {
			public static TargetOrgIdenInfor DUMMY = TargetOrgIdenInfor.creatIdentifiWorkplace("wkpId");
		}
		
		public static class DispsetOrg {
			public static DisplaySettingByDateForOrganization create(DisplaySettingByDate dispset) {
				return new DisplaySettingByDateForOrganization(TargetOrg.DUMMY, dispset);
			}
		}
		
		public static class DispsetCom {
			public static  DisplaySettingByDateForCompany create(DisplaySettingByDate dispset) {
				return new DisplaySettingByDateForCompany(dispset);
			}
		}
	}

}
