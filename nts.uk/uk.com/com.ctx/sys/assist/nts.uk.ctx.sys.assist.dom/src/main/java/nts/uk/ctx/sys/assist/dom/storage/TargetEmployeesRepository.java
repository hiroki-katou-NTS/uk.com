package nts.uk.ctx.sys.assist.dom.storage;

import java.util.Optional;
import java.util.List;

/**
* データ保存の対象社員
*/
public interface TargetEmployeesRepository
{

    List<TargetEmployees> getAllTargetEmployees();

    Optional<TargetEmployees> getTargetEmployeesById(String storeProcessingId, String employeeId);

}
