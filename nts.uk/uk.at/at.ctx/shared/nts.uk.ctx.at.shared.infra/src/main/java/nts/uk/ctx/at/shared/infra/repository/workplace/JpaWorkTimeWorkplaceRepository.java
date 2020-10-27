package nts.uk.ctx.at.shared.infra.repository.workplace;

import java.util.List;
//import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWt;
//import nts.uk.ctx.at.shared.infra.entity.workplace.KshmtWorkTimeWorkplacePK;
import nts.uk.ctx.at.shared.infra.repository.worktime.worktimeset.JpaWorkTimeSettingGetMemento;

@Stateless
public class JpaWorkTimeWorkplaceRepository extends JpaRepository implements WorkTimeWorkplaceRepository {

	private static final String SELECT_WORKTIME_WORKPLACE_BYID = "SELECT a FROM KshmtWt a JOIN KshmtWorkTimeWorkplace b "
			+ " ON a.kshmtWtPK.cid = b.kshmtWorkTimeWorkplacePK.companyID "
			+ " AND a.kshmtWtPK.worktimeCd = b.kshmtWorkTimeWorkplacePK.workTimeID "
			+ " WHERE b.kshmtWorkTimeWorkplacePK.companyID = :companyID "
			+ " AND b.kshmtWorkTimeWorkplacePK.workplaceID = :workplaceID "
			+ " AND a.abolitionAtr = 1";
	
	@Override
	public List<WorkTimeSetting> getWorkTimeWorkplaceById(String companyID, String workplaceID) {
		List<WorkTimeSetting> getWorkTimeWorkplaceById = this.queryProxy()
				.query(SELECT_WORKTIME_WORKPLACE_BYID, KshmtWt.class)
				.setParameter("companyID", companyID)
				.setParameter("workplaceID", workplaceID)
				.getList(c-> new WorkTimeSetting(new JpaWorkTimeSettingGetMemento(c)));
		return getWorkTimeWorkplaceById;
	}

}
