package nts.uk.ctx.pr.report.app.find.printconfig.empinsreportsetting;

import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EmpInsReportTxtSettingFinder {

    @Inject
    private EmpInsReportTxtSettingRepository empInsReportTxtSetting;

    public EmpInsReportTxtSettingDto getEmpInsReportTxtSetting(){
        String cId = AppContexts.user().companyId();
        String userId = AppContexts.user().userId();
        return empInsReportTxtSetting.getEmpInsReportTxtSettingByUserId(cId, userId).map(item -> EmpInsReportTxtSettingDto.fromDomain(item)).orElse(null);
    }

}
