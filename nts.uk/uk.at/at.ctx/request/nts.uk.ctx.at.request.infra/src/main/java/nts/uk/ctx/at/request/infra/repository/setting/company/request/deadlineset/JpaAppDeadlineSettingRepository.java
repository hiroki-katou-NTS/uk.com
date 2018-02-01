package nts.uk.ctx.at.request.infra.repository.setting.company.request.deadlineset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.deadlinesetting.AppDeadlineSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.deadlinesetting.AppDeadlineSettingRepository;
import nts.uk.ctx.at.request.infra.entity.setting.request.application.KrqstAppDeadline;
@Stateless
public class JpaAppDeadlineSettingRepository extends JpaRepository implements AppDeadlineSettingRepository{

	private static final String FINDBYCLOSURE = "SELECT c "
			+ "FROM KrqstAppDeadline c "
			+ "WHERE c.krqstAppDeadlinePK.companyId = :companyId "
			+ "AND c.krqstAppDeadlinePK.closureId = :closureId";
	
	/**
	 * @author hoatt
	 * get deadline setting by closureId
	 */
	@Override
	public Optional<AppDeadlineSetting> getDeadlineByClosureId(String companyId, int closureId) {
		// TODO Auto-generated method stub
		return this.queryProxy()
				.query(FINDBYCLOSURE, KrqstAppDeadline.class)
				.setParameter("companyId", companyId)
				.setParameter("closureId", closureId)
				.getSingle(c -> toDomain(c));
	}
	
	private AppDeadlineSetting toDomain(KrqstAppDeadline c) {
		return AppDeadlineSetting.createSimpleFromJavaType(c.krqstAppDeadlinePK.companyId,
				c.krqstAppDeadlinePK.closureId, 
				c.useAtr, 
				c.deadline, 
				c.deadlineCriteria);
	}
}
