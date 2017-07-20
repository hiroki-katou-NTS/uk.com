package nts.uk.ctx.sys.portal.infra.repository.webmenu.jobtitletying;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.portal.dom.webmenu.jobtitletying.JobTitleTying;
import nts.uk.ctx.sys.portal.dom.webmenu.jobtitletying.JobTitleTyingRepository;
import nts.uk.ctx.sys.portal.infra.entity.webmenu.jobtitletying.CcgstJobTitleTying;
import nts.uk.ctx.sys.portal.infra.entity.webmenu.jobtitletying.CcgstJobTitleTyingPK;

/**
 * @author yennth The Class JpaJobTitleTyingRepository.
 */
@Stateless
public class JpaJobTitleTyingRepository extends JpaRepository implements JobTitleTyingRepository {
	private final String SEL = "SELECT s FROM CcgstJobTitleTying s ";
	private final String FIND_WEB_MENU_CODE = SEL + "WHERE s.ccgstJobTitleTyingPK.companyId = :companyId "
			+ "AND s.ccgstJobTitleTyingPK.jobId in :jobId ";

	@Override
	public void updateAndInsertMenuCode(List<JobTitleTying> JobTitleTying) {
		EntityManager manager = this.getEntityManager();
		CcgstJobTitleTyingPK pk;
		for (JobTitleTying obj : JobTitleTying) {
			pk = new CcgstJobTitleTyingPK(obj.getCompanyId(), obj.getJobId());
			CcgstJobTitleTying jtt = manager.find(CcgstJobTitleTying.class, pk);
			if (jtt != null)
				jtt.setWebMenuCode(obj.getWebMenuCode());
			else {
				String webMenuCode = StringUtil.isNullOrEmpty(obj.getWebMenuCode(), true) ? "   " : obj.getWebMenuCode(); // fix on oracle
				CcgstJobTitleTying jttYen = new CcgstJobTitleTying(pk, webMenuCode);
				manager.persist(jttYen);
			}
		}
	}

	private JobTitleTying toDomain(CcgstJobTitleTying s) {
		return JobTitleTying.createFromJavaType(s.ccgstJobTitleTyingPK.companyId, s.ccgstJobTitleTyingPK.jobId,
				s.webMenuCode);
	}

	@Override
	public List<JobTitleTying> findWebMenuCode(String companyId, List<String> jobId) {
		return this.queryProxy().query(FIND_WEB_MENU_CODE, CcgstJobTitleTying.class)
				.setParameter("companyId", companyId).setParameter("jobId", jobId).getList(t -> toDomain(t));
	}

	@Override
	public void validate(JobTitleTying obj) {
		if (obj.getWebMenuCode() == null) {
			throw new RuntimeException("Not Found WebMenuCode:" + obj.getWebMenuCode());
		}
	}
}
