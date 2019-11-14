package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSettingRepository;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class EmpInsGetQualifReportPdfService extends ExportService<EmpInsGetQualifReportQuery> {

    @Inject
    private EmpInsReportSettingRepository settingRepository;

    @Inject
    private EmpInsReportTxtSettingRepository txtSettingRepository;

    @Inject
    private EmpInsGetQualifRptFileGenerator generator;

    @Override
    protected void handle(ExportServiceContext<EmpInsGetQualifReportQuery> exportServiceContext) {
        String cid = AppContexts.user().companyId();
        String userId = AppContexts.user().userId();
        EmpInsReportSettingExport settingExport = exportServiceContext.getQuery().getEmpInsReportSettingExport();
        EmpInsRptTxtSettingExport txtSettingExport = exportServiceContext.getQuery().getEmpInsRptTxtSettingExport();
        GeneralDate fillingDate = exportServiceContext.getQuery().getFillingDate();

        EmpInsReportSetting reportSetting = new EmpInsReportSetting(
                cid,
                userId,
                settingExport.getSubmitNameAtr(),
                settingExport.getOutputOrderAtr(),
                settingExport.getOfficeClsAtr(),
                settingExport.getMyNumberClsAtr(),
                settingExport.getNameChangeClsAtr()
        );

        EmpInsReportTxtSetting reportTxtSetting = new EmpInsReportTxtSetting(
                cid,
                userId,
                txtSettingExport.getOfficeAtr(),
                txtSettingExport.getFdNumber(),
                txtSettingExport.getLineFeedCode()
        );

        List<EmpInsGetQualifReport> listDataExport = new ArrayList<>();





        generator.generate(exportServiceContext.getGeneratorContext(), listDataExport);
    }
}
