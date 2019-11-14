package nts.uk.ctx.pr.report.app.find.printconfig.empinsreportsetting;

import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class EmpInsRptTxtSettingFinder {

    @Inject
    EmpInsReportTxtSettingRepository finder;

    public List<EmpInsRptTxtSettingDto> getAllEmpInsReportTxtSetting() {
        return finder.getAllEmpInsReportTxtSetting(AppContexts.user().companyId()).stream()
                .map(EmpInsRptTxtSettingDto::fromDomainToDto)
                .collect(Collectors.toList());
    }

    public EmpInsRptTxtSettingDto getEmpInsReportTxtSettingByUserId() {
        return finder.getEmpInsReportTxtSettingByUserId(AppContexts.user().companyId(), AppContexts.user().userId())
                .map(EmpInsRptTxtSettingDto::fromDomainToDto).orElse(null);
    }

}
