package nts.uk.ctx.sys.portal.infra.repository.jobtitletying;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.jobtitletying.JobTitleTying;
import nts.uk.ctx.sys.portal.dom.jobtitletying.JobTitleTyingRepository;
import nts.uk.ctx.sys.portal.infra.entity.jobtitletying.CcgstJobTitleTying;
import nts.uk.ctx.sys.portal.infra.entity.jobtitletying.CcgstJobTitleTyingPK;
/**
 * @author yennth
 * The Class JpaJobTitleTyingRepository.
 */
@Stateless
public class JpaJobTitleTyingRepository extends JpaRepository implements JobTitleTyingRepository{
	private final String SEL = "SELECT s FROM CcgstJobTitleTying s ";
	private final String FIND_WEB_MENU_CODE = SEL + "WHERE s.ccgstJobTitleTyingPK.companyId = :companyId "
			+ "AND s.ccgstJobTitleTyingPK.jobId = :jobId ";
	
	private CcgstJobTitleTying toEntity(JobTitleTying domain) {
		val entity = new CcgstJobTitleTying();
		entity.ccgstJobTitleTyingPK = new CcgstJobTitleTyingPK();
		entity.ccgstJobTitleTyingPK.companyId = domain.getCompanyId();
		entity.ccgstJobTitleTyingPK.jobId = domain.getJobId();
		entity.webMenuCode = domain.getWebMenuCode();
		return entity;
	}
	
	@Override
	public void changeMenuCode(List<JobTitleTying> JobTitleTying){
		EntityManager manager = this.getEntityManager();
		CcgstJobTitleTyingPK pk;
		for(JobTitleTying obj: JobTitleTying){
			pk = new CcgstJobTitleTyingPK(obj.getCompanyId(), obj.getJobId()); 
			CcgstJobTitleTying jtt = manager.find(CcgstJobTitleTying.class, pk);
			jtt.setWebMenuCode(obj.getWebMenuCode());
		}
	}
	
	@Override
	public boolean isExistWebMenuCode(List<JobTitleTying> JobTitleTying) {
		boolean isExist = false;
		for (JobTitleTying obj : JobTitleTying) {
			if (obj.getWebMenuCode() != null || !obj.getWebMenuCode().equals(""))
				isExist = true;
			break;
		}
		return isExist;
	}
	
	
	private JobTitleTying toDomain(CcgstJobTitleTying s) {
		return JobTitleTying.createFromJavaType(s.ccgstJobTitleTyingPK.companyId, s.ccgstJobTitleTyingPK.jobId,
				s.webMenuCode);
	}
	
	@Override
	public List<JobTitleTying> findWebMenuCode(String companyId, String jobId){
		return this.queryProxy().query(FIND_WEB_MENU_CODE, CcgstJobTitleTying.class)
				.setParameter("companyId", companyId)
				.setParameter("jobId", jobId)
				.getList(t -> toDomain(t));
	}
}
