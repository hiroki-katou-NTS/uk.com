package nts.uk.ctx.sys.auth.pub.employee;

import java.util.List;
import java.util.Optional;

public interface EmployeePublisher {
	Optional<NarrowEmpByReferenceRange> findByEmpId (List<String> sID);
}
