package nts.uk.ctx.workflow.infra.repository.approvermanagement.setting;

import javax.ejb.Stateless;

import lombok.val;
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
	/**
	 * convert from job assign setting domain to entity 
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private static WwfstJobAssignSetting toEntity(JobAssignSetting domain){
		val entity = new WwfstJobAssignSetting();
		entity.companyId = domain.getCompanyId();
		entity.isConcurrently = domain.getIsConcurrently() == true ? 1 : 0;
		return entity;
	}
	
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private static JobAssignSetting toDomainJob(WwfstJobAssignSetting entity){
		JobAssignSetting jobAssign = JobAssignSetting.createFromJavaType(entity.companyId, entity.isConcurrently == 1 ? true : false);
		return jobAssign;
	}
	
	/**
	 * update job assign setting
	 * @author yennth
	 */
	@Override
	public void updateJob(JobAssignSetting jobAssign) {
		WwfstJobAssignSetting entity = toEntity(jobAssign);
		WwfstJobAssignSetting oldEntity = this.queryProxy().find(entity.companyId, WwfstJobAssignSetting.class).get();
		oldEntity.isConcurrently = entity.isConcurrently;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert job assign setting
	 * @author yennth
	 */
	@Override
	public void insertJob(JobAssignSetting jobAssign) {
		WwfstJobAssignSetting entity = toEntity(jobAssign);
		this.commandProxy().insert(entity);
	}

}
