package nts.uk.ctx.sys.shared.dom.employee.syworkplace;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface SyaWkpHistAdapter {

	Optional<SyaWkpHistImport> findBySid(String employeeId, GeneralDate baseDate);
}
