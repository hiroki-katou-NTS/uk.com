package nts.uk.ctx.pr.report.app.find.printconfig.empinsreportsetting;

import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EmpInsReportTxtSettingFinder {

    @Inject
    private EmpInsReportTxtSettingRepository empInsReportTxtSetting;

    String CID = AppContexts.user().companyId();
    String SID = AppContexts.user().userId();

    public EmpInsReportTxtSettingDto getEmpInsReportTxtSetting(){
        return empInsReportTxtSetting.getEmpInsReportTxtSettingByUserId(CID, SID).map(item -> EmpInsReportTxtSettingDto.fromDomain(item)).orElse(null);
    }

}
