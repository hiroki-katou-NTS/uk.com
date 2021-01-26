package nts.uk.ctx.sys.shared.dom.employee.employment;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface SyaEmpHistAdapter {
	
	Optional<SyaEmpHistImport> findBySid(String employeeId, GeneralDate baseDate);
}
