package nts.uk.ctx.pr.transfer.ac.core.emprsdttaxinfo;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.pub.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoPub;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoImport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmployeeResidentTaxPayeeInfoAdapterImpl implements EmployeeResidentTaxPayeeInfoAdapter {
    @Inject
    private EmployeeResidentTaxPayeeInfoPub employeeResidentTaxPayeeInfoPub;

    @Override
    public List<EmployeeResidentTaxPayeeInfoImport> getEmpRsdtTaxPayeeInfo(List<String> listSId, YearMonth periodYM) {
        return employeeResidentTaxPayeeInfoPub.getEmpRsdtTaxPayeeInfo(listSId, periodYM).stream().map(x -> {
            EmployeeResidentTaxPayeeInfoImport imp = new EmployeeResidentTaxPayeeInfoImport();
            imp.setSid(x.getSid());
            imp.setHistoryItems(x.getHistoryItems());
            return imp;
        }).collect(Collectors.toList());
    }
}
