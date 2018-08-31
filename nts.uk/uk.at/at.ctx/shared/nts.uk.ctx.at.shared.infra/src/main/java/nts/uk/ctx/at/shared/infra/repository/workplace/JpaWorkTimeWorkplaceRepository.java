package nts.uk.ctx.at.shared.infra.repository.workplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplace;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplaceRepository;
import nts.uk.ctx.at.shared.infra.entity.workplace.KshmtWorkTimeWorkplace;
import nts.uk.ctx.at.shared.infra.entity.workplace.KshmtWorkTimeWorkplacePK;

@Stateless
public class JpaWorkTimeWorkplaceRepository extends JpaRepository implements WorkTimeWorkplaceRepository {

	private static final String SELECT_WORKTIME_WORKPLACE_BYID = "SELECT a FROM KshmtWorkTimeWorkplace a "
			+ " WHERE a.kshmtWorkTimeWorkplacePK.companyID = :companyID "
			+ " AND a.kshmtWorkTimeWorkplacePK.workplaceID = :workplaceID ";
	
	private WorkTimeWorkplace toDomain(KshmtWorkTimeWorkplace entity) {
		return WorkTimeWorkplace.createFromJavaType(
				entity.kshmtWorkTimeWorkplacePK.companyID, 
				entity.kshmtWorkTimeWorkplacePK.workplaceID, 
				entity.kshmtWorkTimeWorkplacePK.workTimeID);
	}
	
	private KshmtWorkTimeWorkplace toEntity(WorkTimeWorkplace domain) {
		return new  KshmtWorkTimeWorkplace(new KshmtWorkTimeWorkplacePK(
				domain.getCompanyID(),
				domain.getWorkplaceID(),
				domain.getWorkTimeID()
				));
	}
	
	@Override
	public List<String> getWorkTimeWorkplaceById(String companyID, String workplaceID) {
		List<String> getWorkTimeWorkplaceById = new ArrayList<>();
		List<WorkTimeWorkplace> listWorkTimeWorkplace =  this.queryProxy()
				.query(SELECT_WORKTIME_WORKPLACE_BYID,KshmtWorkTimeWorkplace.class)
				.setParameter("companyID", companyID)
				.setParameter("workplaceID", workplaceID)
				.getList(c-> toDomain(c));
		for(WorkTimeWorkplace workTimeWorkplace : listWorkTimeWorkplace) {
			getWorkTimeWorkplaceById.add(workTimeWorkplace.getWorkTimeID());
		}
		
		return getWorkTimeWorkplaceById;
	}

}
