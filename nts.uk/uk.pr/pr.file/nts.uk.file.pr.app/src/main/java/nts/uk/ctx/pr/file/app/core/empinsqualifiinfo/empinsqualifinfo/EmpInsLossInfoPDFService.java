package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import lombok.experimental.var;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSettingRepository;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSettingRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfoRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHistRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmpInsLossInfoPDFService extends ExportService<EmpInsGetQualifReportQuery>{

    @Inject
    private EmpInsReportSettingRepository empInsReportSettingRepository;

    @Inject
    EmpInsReportTxtSettingRepository empInsReportTxtSettingRepository;

    @Inject
    private EmpInsHistRepository empInsHistRepository;

    @Inject
    private JapaneseErasAdapter japaneseErasAdapter;

    @Inject
    private EmpInsGetInfoRepository empInsGetInfoRepository;

    @Override
    protected void handle(ExportServiceContext<EmpInsGetQualifReportQuery> exportServiceContext) {
        String cid = AppContexts.user().companyId();
        String userId = AppContexts.user().userId();
        GeneralDate fillingDate = exportServiceContext.getQuery().getFillingDate();
        GeneralDate startDate = exportServiceContext.getQuery().getStartDate();
        GeneralDate endDate = exportServiceContext.getQuery().getEndDate();
        List<String> listEmpId = exportServiceContext.getQuery().getEmpIds();
        EmpInsReportSettingExport reportSettingExport = exportServiceContext.getQuery().getEmpInsReportSetting();
        EmpInsRptTxtSettingExport reportTxtSettingExport = exportServiceContext.getQuery().getEmpInsRptTxtSetting();
        // update setting
        EmpInsReportSetting reportSetting = new EmpInsReportSetting(
                cid,
                userId,
                reportSettingExport.getSubmitNameAtr(),
                reportSettingExport.getOutputOrderAtr(),
                reportSettingExport.getOfficeClsAtr(),
                reportSettingExport.getMyNumberClsAtr(),
                reportSettingExport.getNameChangeClsAtr()
        );

        EmpInsReportTxtSetting reportTxtSetting = new EmpInsReportTxtSetting(
                cid,
                userId,
                reportTxtSettingExport.getOfficeAtr(),
                reportTxtSettingExport.getFdNumber(),
                reportTxtSettingExport.getLineFeedCode()
        );

        empInsReportSettingRepository.update(reportSetting);
        empInsReportTxtSettingRepository.update(reportTxtSetting);

        if(endDate.before(startDate)) {
            throw new BusinessException("Msg_812");
        }

        List<EmpInsHist> empInsHists = empInsHistRepository.getByEmpIdsAndStartDate(listEmpId, startDate);
        List<String> empInsHistEmpIds = empInsHists.stream().map(EmpInsHist::getSid).collect(Collectors.toList());
//        List<EmpInsGetInfo> empInsGetInfos = empInsGetInfoRepository.getByEmpIds(empInsHistEmpIds);
        if (empInsHists.isEmpty()) {
            throw new BusinessException("Msg_51");
        }

        empInsHists.forEach(e->{
//            var acquisitionAtr = empInsGetInfos.stream().filter(item -> item.getSalaryId().equalsIgnoreCase(e.getSid()));

        });
    }
}
