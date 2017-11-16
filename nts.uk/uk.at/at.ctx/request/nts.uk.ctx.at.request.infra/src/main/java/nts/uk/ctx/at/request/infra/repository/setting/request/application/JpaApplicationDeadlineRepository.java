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
			+ "FROM KrqstAppDeadline c "
			+ "WHERE c.krqstAppDeadlinePK.companyId = :companyId "
			+ "AND c.krqstAppDeadlinePK.closureId = :closureId";
	private static final String FINDBYCOMPANY = "SELECT c "
			+ "FROM KrqstAppDeadline c "
			+ "WHERE c.krqstAppDeadlinePK.companyId = :company ";

	/**
	 * get deadline setting by closureId
	 */
	@Override
	public Optional<ApplicationDeadline> getDeadlineByClosureId(String companyId, int closureId) {
		Optional<ApplicationDeadline> data = this.queryProxy()
				.query(FINDBYCLOSURE, KrqstAppDeadline.class)
				.setParameter("companyId", companyId)
				.setParameter("closureId", closureId)
				.getSingle(c -> toDomain(c));

		return data;
	}
	private ApplicationDeadline toDomain(KrqstAppDeadline c) {
		return ApplicationDeadline.createSimpleFromJavaType(c.krqstAppDeadlinePK.companyId,
				c.krqstAppDeadlinePK.closureId, 
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
				.query(FINDBYCOMPANY, KrqstAppDeadline.class)
				.setParameter("companyId", companyId)
				.getList(c -> toDomain(c));

		return data;
	}


}
