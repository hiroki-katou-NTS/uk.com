package nts.uk.ctx.pr.core.pubimp.emprsdttaxinfo;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoRepository;
import nts.uk.ctx.pr.core.pub.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoExport;
import nts.uk.ctx.pr.core.pub.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmployeeResidentTaxPayeeInfoPubImpl implements EmployeeResidentTaxPayeeInfoPub {
    @Inject
    private EmployeeResidentTaxPayeeInfoRepository empRsdtTaxPayeeInfoRepo;

    @Override
    public List<EmployeeResidentTaxPayeeInfoExport> getEmpRsdtTaxPayeeInfo(List<String> listSId, YearMonth periodYM) {
        return empRsdtTaxPayeeInfoRepo.getEmpRsdtTaxPayeeInfo(listSId, periodYM).stream().map(x -> {
            EmployeeResidentTaxPayeeInfoExport export = new EmployeeResidentTaxPayeeInfoExport();
            export.setSid(x.getSid());
            export.setHistoryItems(x.items());
            return export;
        }).collect(Collectors.toList());
    }
}
