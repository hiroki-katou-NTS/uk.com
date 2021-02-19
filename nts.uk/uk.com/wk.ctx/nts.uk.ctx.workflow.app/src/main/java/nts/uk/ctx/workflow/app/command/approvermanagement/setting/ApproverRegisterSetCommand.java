package nts.uk.ctx.workflow.app.command.approvermanagement.setting;


import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApproverRegisterSet;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.UseClassification;

public class ApproverRegisterSetCommand {
	/** 会社単位  */
	public int companyUnit;
	/** 職場単位  */
	public int workplaceUnit;
	/** 社員単位  */
	public int employeeUnit;
	
	public ApproverRegisterSet toDomain() {
		return new ApproverRegisterSet(
				EnumAdaptor.valueOf(companyUnit, UseClassification.class), 
				EnumAdaptor.valueOf(workplaceUnit, UseClassification.class), 
				EnumAdaptor.valueOf(employeeUnit, UseClassification.class));
	}
}
