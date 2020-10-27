package nts.uk.ctx.workflow.infra.repository.approvermanagement.setting;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSettingRepository;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.setting.WwfmtJobAssignting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaJobAssignSettingRepository extends JpaRepository implements JobAssignSettingRepository {

	@Override
	public Optional<JobAssignSetting> findById() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().find(companyId, WwfmtJobAssignting.class)
				.map(c -> toDomainJob(c));
	}
	/**
	 * convert from job assign setting domain to entity 
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private static WwfmtJobAssignting toEntity(JobAssignSetting domain){
		val entity = new WwfmtJobAssignting();
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
	private static JobAssignSetting toDomainJob(WwfmtJobAssignting entity){
		JobAssignSetting jobAssign = JobAssignSetting.createFromJavaType(entity.companyId, entity.isConcurrently == 1 ? true : false);
		return jobAssign;
	}
	
	/**
	 * update job assign setting
	 * @author yennth
	 */
	@Override
	public void updateJob(JobAssignSetting jobAssign) {
		WwfmtJobAssignting entity = toEntity(jobAssign);
		WwfmtJobAssignting oldEntity = this.queryProxy().find(entity.companyId, WwfmtJobAssignting.class).get();
		oldEntity.isConcurrently = entity.isConcurrently;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert job assign setting
	 * @author yennth
	 */
	@Override
	public void insertJob(JobAssignSetting jobAssign) {
		WwfmtJobAssignting entity = toEntity(jobAssign);
		this.commandProxy().insert(entity);
	}

}
