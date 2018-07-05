package nts.uk.ctx.sys.portal.infra.repository.webmenu.jobtitletying;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.webmenu.jobtitletying.JobTitleTying;
import nts.uk.ctx.sys.portal.dom.webmenu.jobtitletying.JobTitleTyingRepository;
import nts.uk.ctx.sys.portal.infra.entity.webmenu.jobtitletying.CcgstJobTitleTying;
import nts.uk.ctx.sys.portal.infra.entity.webmenu.jobtitletying.CcgstJobTitleTyingPK;

/**
 * @author yennth The Class JpaJobTitleTyingRepository.
 */
@Stateless
public class JpaJobTitleTyingRepository extends JpaRepository implements JobTitleTyingRepository {
	
	private static final String SEL = "SELECT s FROM CcgstJobTitleTying s ";
	
	private static final String FIND_WEB_MENU_CODE = SEL + "WHERE s.ccgstJobTitleTyingPK.companyId = :companyId "
			+ "AND s.ccgstJobTitleTyingPK.jobId in :jobId ";
	
	private static final String DELETE_WEB_MENU_CODE = "DELETE FROM CcgstJobTitleTying s WHERE s.ccgstJobTitleTyingPK.companyId = :companyId"
			+ " AND s.webMenuCode = :webMenuCode ";
	
	private static final String DELETE_BY_LIST_JOBID = "DELETE FROM CcgstJobTitleTying s WHERE s.ccgstJobTitleTyingPK.companyId = :companyId"
			+ " AND s.ccgstJobTitleTyingPK.jobId IN :listJobId";


	/**
	 * convert entity to domain CcgstJobTitleTying
	*/	
	private JobTitleTying toDomain(CcgstJobTitleTying s) {
		
		return JobTitleTying.createFromJavaType(s.ccgstJobTitleTyingPK.companyId, s.ccgstJobTitleTyingPK.jobId,
				s.webMenuCode);
	}
	
	/**
	 * convert domain JobTitleTying to entity CcgstJobTitleTying
	 * @param domain
	 * @return
	 */
	private static CcgstJobTitleTying toEntity(JobTitleTying domain) {
		val entity = new CcgstJobTitleTying();
		entity.ccgstJobTitleTyingPK = new CcgstJobTitleTyingPK(domain.getCompanyId(), domain.getJobId());
		entity.webMenuCode = domain.getWebMenuCode();
		return entity;
	}
	/**
	 * find web menu code by companyId and a list jobId
	 */
	@Override
	public List<JobTitleTying> findWebMenuCode(String companyId, List<String> jobId) {
		return this.queryProxy().query(FIND_WEB_MENU_CODE, CcgstJobTitleTying.class)
				.setParameter("companyId", companyId).setParameter("jobId", jobId).getList(t -> toDomain(t));
	}
	
	/**
	 * insert menu code 
	 */
	@Override
	public void insertMenuCode(List<JobTitleTying> JobTitleTying) {
		List<CcgstJobTitleTying> lstEntity = new ArrayList<>();
		for (JobTitleTying JobTitleTyingItem : JobTitleTying) {
			lstEntity.add(toEntity(JobTitleTyingItem));
		}
		this.commandProxy().insertAll(lstEntity);
	}
	
	@Override
	public void removeByMenuCode(String companyId, String webMenuCode) {
		this.getEntityManager().createQuery(DELETE_WEB_MENU_CODE)
		.setParameter("companyId", companyId)
		.setParameter("webMenuCode", webMenuCode)
		.executeUpdate();
	}

	@Override
	public void removeByListJobId(String companyId, List<String> listJobId) {
		this.getEntityManager().createQuery(DELETE_BY_LIST_JOBID)
		.setParameter("companyId", companyId)
		.setParameter("listJobId", listJobId)
		.executeUpdate();
	}
}
