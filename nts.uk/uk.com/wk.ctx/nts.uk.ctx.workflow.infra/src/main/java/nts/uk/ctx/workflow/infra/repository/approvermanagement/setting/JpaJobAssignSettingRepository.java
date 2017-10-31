package nts.uk.ctx.workflow.infra.repository.approvermanagement.setting;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSettingRepository;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.setting.WwfstJobAssignSetting;

@Stateless
public class JpaJobAssignSettingRepository extends JpaRepository implements JobAssignSettingRepository {
	private final String FIND_BY_ID = "SELECT c FROM WwfstJobAssignSetting c WHERE c.companyId = :companyId";

	@Override
	public JobAssignSetting findById(String companyId) {
		return this.queryProxy().query(FIND_BY_ID, WwfstJobAssignSetting.class)
				.setParameter("companyId", companyId)
				.getSingleOrNull(c -> new JobAssignSetting(c.companyId, c.isConcurrently == 0 ? false : true));
	}

}
