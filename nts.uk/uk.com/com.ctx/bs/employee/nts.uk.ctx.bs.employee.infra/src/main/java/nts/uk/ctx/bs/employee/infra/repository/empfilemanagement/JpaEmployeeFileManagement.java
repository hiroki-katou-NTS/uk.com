package nts.uk.ctx.bs.employee.infra.repository.empfilemanagement;

import java.util.List;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmpFileManagementRepository;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmployeeFileManagement;


@Stateless
@Transactional
public class JpaEmployeeFileManagement  extends JpaRepository implements EmpFileManagementRepository{
	
	private static final String GET_DATA_BY_SID_AND_TYPE_FILE= "";

	@Override
	public List<EmployeeFileManagement> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(EmployeeFileManagement domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(EmployeeFileManagement domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EmployeeFileManagement> getDataByParams(String employeeId, int fileType) {
		// TODO Auto-generated method stub
		return null;
	}

}
