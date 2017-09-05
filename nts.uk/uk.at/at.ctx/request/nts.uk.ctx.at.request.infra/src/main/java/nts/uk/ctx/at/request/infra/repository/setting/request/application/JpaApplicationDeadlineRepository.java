package nts.uk.ctx.at.request.infra.repository.setting.request.application;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadlineRepository;
import nts.uk.ctx.at.request.infra.entity.setting.request.application.KrqstAppDeadline;

@Stateless
public class JpaApplicationDeadlineRepository extends JpaRepository implements ApplicationDeadlineRepository{
	private static final String FINDBYCLOSURE = "SELECT c "
			+ "FROM KrqstAppDeadline "
			+ "WHERE c.krqstAppDeadlinePK.companyId = :company "
			+ "AND c.krqstAppDeadlinePK.closureId = :closureId";
	private static final String FINDBYAPPTYPE = "SELECT c "
			+ "FROM KrqstAppDeadline "
			+ "WHERE c.krqstAppDeadlinePK.companyId = :company "
			+ "AND c.krqstAppDeadlinePK.appType = :appType";
			
	private static final String FINDBYCLOSUREANDAPPTYPE= "SELECT c "
			+ "FROM KrqstAppDeadline "
			+ "WHERE c.krqstAppDeadlinePK.companyId = :company "
			+ "AND c.krqstAppDeadlinePK.closureId = :closureId "
			+ "AND c.krqstAppDeadlinePK.appType = :appType ";
	/**
	 * get deadline setting by closureId
	 */
	@Override
	public List<ApplicationDeadline> getDeadlineByClosureId(String companyId, int closureId) {
		List<ApplicationDeadline> data = this.queryProxy()
				.query(FINDBYCLOSURE, KrqstAppDeadline.class)
				.setParameter("companyId", companyId)
				.setParameter("closureId", closureId)
				.getList(c -> toDomain(c));

		return data;
	}
	private ApplicationDeadline toDomain(KrqstAppDeadline c) {
		return ApplicationDeadline.createSimpleFromJavaType(c.krqstAppDeadlinePK.companyId,
				c.krqstAppDeadlinePK.closureId, 
				c.krqstAppDeadlinePK.appType, 
				c.useAtr, 
				c.deadline, 
				c.deadlineCriteria);
	}
	/**
	 * get deadline setting by app type
	 */
	@Override
	public List<ApplicationDeadline> getDeadlineByAppType(String companyId, int appType) {
		List<ApplicationDeadline> data = this.queryProxy()
				.query(FINDBYAPPTYPE, KrqstAppDeadline.class)
				.setParameter("companyId", companyId)
				.setParameter("appType", appType)
				.getList(c -> toDomain(c));

		return data;
	}
	/**
	 * get dealine setting by closure id and app type
	 */
	@Override
	public Optional<ApplicationDeadline> getDeadlineByClosurIdAndAppType(String companyId, int closureId, int appType) {
		Optional<ApplicationDeadline> data = this.queryProxy()
				.query(FINDBYCLOSUREANDAPPTYPE, KrqstAppDeadline.class)
				.setParameter("companyId", companyId)
				.setParameter("closureId", closureId)
				.setParameter("appType", appType)
				.getSingle(c -> toDomain(c));

		return data;
	}

}
