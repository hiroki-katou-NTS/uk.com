package nts.uk.ctx.workflow.dom.approvermanagement.setting;

public interface JobAssignSettingRepository {
	/**
	 * finById by companyId
	 * 
	 * @param companyId
	 * @return
	 */
	JobAssignSetting findById(String companyId);
}
