package nts.uk.ctx.at.schedule.infra.repository.employeeinfo.medicalworkstyle;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkFormHisItem;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkStyleHistory;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;

/**
 * 
 * @author HieuLt
 *
 */
@Stateless

public class JpaEmpMedicalWorkStyleHistoryRepository extends JpaRepository implements EmpMedicalWorkStyleHistoryRepository  {
				
	@Override
	public Optional<EmpMedicalWorkFormHisItem> get(String empID, GeneralDate referenceDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpMedicalWorkFormHisItem> get(List<String> listEmp, GeneralDate referenceDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(EmpMedicalWorkStyleHistory empMedicalWorkStyleHistory,
			EmpMedicalWorkFormHisItem empMedicalWorkFormHisItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(EmpMedicalWorkStyleHistory empMedicalWorkStyleHistory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(EmpMedicalWorkFormHisItem empMedicalWorkFormHisItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String empId, String historyId) {
		// TODO Auto-generated method stub
		
	}

}
