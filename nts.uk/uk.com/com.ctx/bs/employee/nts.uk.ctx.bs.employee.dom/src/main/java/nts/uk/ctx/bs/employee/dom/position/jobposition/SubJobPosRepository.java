package nts.uk.ctx.bs.employee.dom.position.jobposition;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface SubJobPosRepository {

	public List<SubJobPosition> getSubJobPosByDeptId(String deptId);

	public Optional<SubJobPosition> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate);
	
}
