package nts.uk.ctx.sys.portal.infra.repository.webmenu.jobtitletying;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.webmenu.jobtitletying.JobTitleTying;
import nts.uk.ctx.sys.portal.dom.webmenu.jobtitletying.JobTitleTyingRepository;
import nts.uk.ctx.sys.portal.infra.entity.webmenu.jobtitletying.CcgstJobTitleTying;
import nts.uk.ctx.sys.portal.infra.entity.webmenu.jobtitletying.CcgstJobTitleTyingPK;
/**
 * @author yennth
 * The Class JpaJobTitleTyingRepository.
 */
@Stateless
public class JpaJobTitleTyingRepository extends JpaRepository implements JobTitleTyingRepository{
	private final String SEL = "SELECT s FROM CcgstJobTitleTying s ";
	private final String FIND_WEB_MENU_CODE = SEL + "WHERE s.ccgstJobTitleTyingPK.companyId = :companyId "
			+ "AND s.ccgstJobTitleTyingPK.jobId in :jobId ";
	
	@Override
	public void changeMenuCode(List<JobTitleTying> JobTitleTying){
		EntityManager manager = this.getEntityManager();
		CcgstJobTitleTyingPK pk;
		for(JobTitleTying obj: JobTitleTying){
			pk = new CcgstJobTitleTyingPK(obj.getCompanyId(), obj.getJobId()); 
			CcgstJobTitleTying jtt = manager.find(CcgstJobTitleTying.class, pk);
			if(jtt != null)
				jtt.setWebMenuCode(obj.getWebMenuCode());
			else{
				CcgstJobTitleTying jttYen = new CcgstJobTitleTying(pk, obj.getWebMenuCode());
				manager.persist(jttYen);
			}
		}
	}
	
	private JobTitleTying toDomain(CcgstJobTitleTying s) {
		return JobTitleTying.createFromJavaType(s.ccgstJobTitleTyingPK.companyId, s.ccgstJobTitleTyingPK.jobId,
				s.webMenuCode);
	}
	
	@Override
	public List<JobTitleTying> findWebMenuCode(String companyId, List<String> jobId){
		return this.queryProxy().query(FIND_WEB_MENU_CODE, CcgstJobTitleTying.class)
				.setParameter("companyId", companyId)
				.setParameter("jobId", jobId)
				.getList(t -> toDomain(t));
	}
}
