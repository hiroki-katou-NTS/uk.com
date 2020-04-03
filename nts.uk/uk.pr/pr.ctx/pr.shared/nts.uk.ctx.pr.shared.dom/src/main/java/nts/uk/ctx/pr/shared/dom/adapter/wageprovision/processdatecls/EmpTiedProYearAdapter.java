package nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

public interface EmpTiedProYearAdapter {
    Optional<EmpTiedProYearImport> getEmpTiedProYearByEmployment(String cid, String employmentCode);

    List<EmpTiedProYearImport> getEmpTiedProYearByEmployments(String cid, List<String> employmentCodes);
}
