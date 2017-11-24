package nts.uk.ctx.bs.employee.infra.repository.department.affiliate;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistory;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryRepository;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
public class JpaAffDepartmentHistoryRepository  extends JpaRepository implements AffDepartmentHistoryRepository{

	@Override
	public Optional<AffDepartmentHistory> getAffDepartmentHistorytByEmployeeId(String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAffDepartment(AffDepartmentHistory domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAffDepartment(AffDepartmentHistory domain, DateHistoryItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAffDepartment(AffDepartmentHistory domain, DateHistoryItem item) {
		// TODO Auto-generated method stub
		
	}

}
