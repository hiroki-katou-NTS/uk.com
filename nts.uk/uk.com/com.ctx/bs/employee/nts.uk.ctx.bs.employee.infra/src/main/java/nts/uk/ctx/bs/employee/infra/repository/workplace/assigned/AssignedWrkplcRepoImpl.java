/**
 * 
 */
package nts.uk.ctx.bs.employee.infra.repository.workplace.assigned;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWorkplace;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWrkplcRepository;
import nts.uk.ctx.bs.employee.infra.entity.workplace.assigned.BsymtAssiWorkplace;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * @author danpv
 *
 */
@Stateless
public class AssignedWrkplcRepoImpl extends JpaRepository implements AssignedWrkplcRepository{

	private static final String SELECT_ASS_WORKPLACE_BY_ID= "SELECT a BsymtAssiWorkplace a"
			+ " WHERE a.bsymtAssiWorkplacePK.assiWorkplaceId = :assiWorkplaceId";
	
	private AssignedWorkplace toDomain(BsymtAssiWorkplace entity){
		return new AssignedWorkplace(entity.getEmpId(), entity.getBsymtAssiWorkplacePK().getAssiWorkplaceId(), 
				entity.getLstBsymtAssiWorkplaceHist().stream()
				.map(x -> new DateHistoryItem(x.getHistoryId(), new DatePeriod(x.getStrD(), x.getEndD()))).collect(Collectors.toList()));
	}
	
	@Override
	public Optional<AssignedWorkplace> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AssignedWorkplace getAssignedWorkplaceById(String assignedWorkplaceId) {
		Optional<AssignedWorkplace> assignedWorkplace = this.queryProxy().query(SELECT_ASS_WORKPLACE_BY_ID, BsymtAssiWorkplace.class)
				.setParameter("assiWorkplaceId", assignedWorkplaceId).getSingle(x -> toDomain(x));
		return assignedWorkplace.isPresent() ? assignedWorkplace.get() : null;
	}

	
}
