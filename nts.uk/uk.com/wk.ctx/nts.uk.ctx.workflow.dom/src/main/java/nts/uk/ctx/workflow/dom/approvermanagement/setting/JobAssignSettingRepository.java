package nts.uk.ctx.workflow.dom.approvermanagement.setting;

public interface JobAssignSettingRepository {
	/**
	 * finById by companyId
	 * @param companyId
	 * @return
	 */
	JobAssignSetting findById(String companyId);
	/**
	 * update job assign setting
	 * @param jobAssign
	 * @author yennth
	 */
	void updateJob(JobAssignSetting jobAssign);
	/**
	 * insert job assign setting
	 * @param jobAssign
	 * @author yennth
	 */
	void insertJob(JobAssignSetting jobAssign);
}
