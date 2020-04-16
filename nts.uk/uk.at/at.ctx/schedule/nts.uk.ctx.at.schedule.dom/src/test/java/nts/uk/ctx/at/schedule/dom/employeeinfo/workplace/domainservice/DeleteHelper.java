package nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.domainservice;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.AffWorkplaceGroup;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceGroup;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceGroupCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceGroupName;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceGroupType;

public class DeleteHelper {
	public static class Helper {
		// String wKPGRPID = "000000000000000000000000000000000011";
		// String cID = AppContexts.user().companyId();
		// String wKPGRPCode = "0000000001";
		// String wKPGRPName = "00000000000000000011";
		// String wKPID = "000000000000000000000000000000000011";
		int wKPGRPType = 1;
		public static WorkplaceGroup DUMMY = new WorkplaceGroup(
				"000000000000000000000000000000000011", 
				"00000000000001", 
				new WorkplaceGroupCode("0000000001"), 
				new WorkplaceGroupName("00000000000000000011"), 
				EnumAdaptor.valueOf(1, WorkplaceGroupType.class));
		
		AffWorkplaceGroup affWorkplaceGroup = new AffWorkplaceGroup(
				"000000000000000000000000000000000011",
				"000000000000000000000000000000000011");
		
	}
}
