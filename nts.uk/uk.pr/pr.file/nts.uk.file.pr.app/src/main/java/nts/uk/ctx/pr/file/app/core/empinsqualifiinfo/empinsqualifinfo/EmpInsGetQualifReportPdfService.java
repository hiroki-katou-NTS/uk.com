package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInforAdapter;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOfficeRepository;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSettingRepository;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSettingRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpEstabInsHistRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpInsOffice;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmpInsGetQualifReportPdfService extends ExportService<EmpInsGetQualifReportQuery> {

    @Inject
    private EmpInsReportSettingRepository reportSettingRepository;

    @Inject
    private EmpInsReportTxtSettingRepository reportTxtSettingRepository;

    @Inject
    private EmpInsGetQualifRptFileGenerator generator;

    @Inject
    private EmpInsHistRepository empInsHistRepository;

    @Inject
    private EmpInsGetInfoRepository empInsGetInfoRepository;

    @Inject
    private EmpInsNumInfoRepository empInsNumInfoRepository;

    @Inject
    private CompanyInforAdapter companyInforAdapter;

    @Inject
    private EmpEstabInsHistRepository empEstabInsHistRepository;

    @Inject
    private LaborInsuranceOfficeRepository laborInsOfficeRepository;

    @Override
    protected void handle(ExportServiceContext<EmpInsGetQualifReportQuery> exportServiceContext) {
        String cid = AppContexts.user().companyId();
        String userId = AppContexts.user().userId();
        GeneralDate fillingDate = exportServiceContext.getQuery().getFillingDate();
        GeneralDate startDate = exportServiceContext.getQuery().getStartDate();
        GeneralDate endDate = exportServiceContext.getQuery().getEndDate();
        List<String> empIds = exportServiceContext.getQuery().getEmpIds();
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

        reportSettingRepository.update(reportSetting);
        reportTxtSettingRepository.update(reportTxtSetting);

        if(endDate.before(startDate)) {
            throw new BusinessException("Msg_812");
        }

        List<EmpInsGetQualifReport> listDataExport = new ArrayList<>();

        List<EmpInsHist> empInsHists = empInsHistRepository.getByEmpIdsAndStartDate(empIds, startDate);
        if (empInsHists.isEmpty()) {
            throw new BusinessException("Msg_51");
        }

        // Pending check マイナンバー印字区分

        List<String> empInsHistEmpIds = empInsHists.stream().map(EmpInsHist::getSid).collect(Collectors.toList());
        List<EmpInsGetInfo> empInsGetInfos = empInsGetInfoRepository.getByEmpIds(empInsHistEmpIds);
        if (empInsGetInfos.isEmpty()) {
            throw new BusinessException("Msg_51");
        }

        List<String> empInsHistIds = empInsHists.stream().map(e -> e.getHistoryItem().get(0).identifier()).collect(Collectors.toList());
        List<EmpInsNumInfo> empInsNumInfos = empInsNumInfoRepository.getByHistIds(empInsHistIds);
        if (empInsNumInfos.isEmpty()) {
            throw new BusinessException("Msg_51");
        }

        switch (reportSetting.getOfficeClsAtr()) {
            case OUPUT_LABOR_OFFICE:
                List<EmpInsOffice> empInsOffices = empEstabInsHistRepository.getByHistIdsAndDate(empInsHistIds, endDate);
//                List<LaborInsuranceOffice> laborInsuranceOffices = laborInsOfficeRepository.getLaborInsuranceOfficeById();
                break;
            case OUTPUT_COMPANY:
                CompanyInfor companyInfo = companyInforAdapter.getCompanyNotAbolitionByCid(cid);
                break;
            default:
                break;
        }

        generator.generate(exportServiceContext.getGeneratorContext(), listDataExport);
    }
}
