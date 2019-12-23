package nts.uk.ctx.pr.shared.dom.adapter.employment;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SystemEmploymentAdapter {

    // RequestList31
    Optional<SEmpHistImport> findSEmpHistBySid(String companyId, String employeeId, GeneralDate baseDate);

    // RequestList31 - ver2
    Map<String, SEmpHistImport> findSEmpHistByListSid(String companyId, List<String> lstSID, GeneralDate baseDate);
}
