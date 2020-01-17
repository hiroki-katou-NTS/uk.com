package nts.uk.ctx.pr.shared.ac.wageprovision.processdatecls;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.EmploymentTiedProcessYmPub;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.EmpTiedProYearAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.EmpTiedProYearImport;
@Stateless
public class EmpTiedProYearAdapterImpl implements EmpTiedProYearAdapter {

    @Inject
    EmploymentTiedProcessYmPub tiedProcessYmPub;

    @Override
    public Optional<EmpTiedProYearImport> getEmpTiedProYearByEmployment(String cid, String employmentCode) {
        return tiedProcessYmPub.getByListEmpCodes(cid, Arrays.asList(employmentCode))
                .map(e -> EmpTiedProYearImport
                        .builder()
                        .cid(e.getCid())
                        .processCateNo(e.getProcessCateNo())
                        .employmentCodes(Arrays.asList(employmentCode))
                        .build());
    }

    @Override
    public List<EmpTiedProYearImport> getEmpTiedProYearByEmployments(String cid, List<String> employmentCodes) {
        return null;
    }
}
