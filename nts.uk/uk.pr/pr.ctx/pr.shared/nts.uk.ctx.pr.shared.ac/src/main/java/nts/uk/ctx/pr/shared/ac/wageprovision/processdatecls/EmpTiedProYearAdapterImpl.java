package nts.uk.ctx.pr.shared.ac.wageprovision.processdatecls;

import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.EmpTiedProYearAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.EmpTiedProYearImport;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
@Stateless
public class EmpTiedProYearAdapterImpl implements EmpTiedProYearAdapter {
    @Override
    public Optional<EmpTiedProYearImport> getEmpTiedProYearByEmployment(String cid, String employmentCode) {
        return Optional.empty();
    }

    @Override
    public List<EmpTiedProYearImport> getEmpTiedProYearByEmployments(String cid, List<String> employmentCodes) {
        return null;
    }
}
