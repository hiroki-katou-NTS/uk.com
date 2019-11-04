package nts.uk.ctx.pr.report.app.find.printconfig.empinsreportsetting;

import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class EmpInsReportSettingFinder {
    @Inject
    EmpInsReportSettingRepository mEmpInsReportSettingRepository;

    public EmpInsReportSettingDto getEmpInsReportSetting(){
        String cid = AppContexts.user().companyId();
        String userId = AppContexts.user().userId();
        Optional<EmpInsReportSetting> resulf =  mEmpInsReportSettingRepository.getEmpInsReportSettingById(cid,userId);
        if(!resulf.isPresent()){
            return resulf.map(empInsReportSetting -> {
                return EmpInsReportSettingDto.builder()
                        .cid(empInsReportSetting.getCid())
                        .userId(empInsReportSetting.getUserId())
                        .submitNameAtr(empInsReportSetting.getSubmitNameAtr().value)
                        .outputOrderAtr(empInsReportSetting.getOutputOrderAtr().value)
                        .officeClsAtr(empInsReportSetting.getOfficeClsAtr().value)
                        .myNumberClsAtr(empInsReportSetting.getMyNumberClsAtr().value)
                        .nameChangeClsAtr(empInsReportSetting.getNameChangeClsAtr().value)
                        .build();
            }).get();
        }
        return EmpInsReportSettingDto.builder()
                .cid(cid)
                .userId(userId)
                .submitNameAtr(0)
                .outputOrderAtr(0)
                .officeClsAtr(0)
                .myNumberClsAtr(0)
                .nameChangeClsAtr(0)
                .build();

    }


}
