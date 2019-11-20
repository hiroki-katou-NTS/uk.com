package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInforAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.ForeignerResHistInfo;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.LaborContractHist;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExportAdapter;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.PublicEmploymentSecurityOffice;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.PublicEmploymentSecurityOfficeRepository;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSettingRepository;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSettingRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpEstabInsHistRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpInsOffice;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseEras;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless
public class EmpInsGetQualifReportCsvService extends ExportService<EmpInsGetQualifReportQuery> {

    @Inject
    private EmpInsReportSettingRepository reportSettingRepository;

    @Inject
    private EmpInsReportTxtSettingRepository reportTxtSettingRepository;

    @Inject
    private EmpInsGetQualifRptCsvFileGenerator generator;

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

    @Inject
    private EmployeeInfoAdapter employeeInfoAdapter;

    @Inject
    private PersonExportAdapter personExportAdapter;

    @Inject
    private JapaneseErasAdapter jpErasAdapter;

    @Inject
    private PublicEmploymentSecurityOfficeRepository pubEmpSecOfficeRepository;

    private static final String MEIJI = "明治";
    private static final String TAISHO = "大正";
    private static final String SHOWA = "昭和";
    private static final String HEISEI = "平成";
    private static final String REIWA = "令和";

    @Override
    protected void handle(ExportServiceContext<EmpInsGetQualifReportQuery> exportServiceContext) {
        String userId = AppContexts.user().userId();
        String cid = AppContexts.user().companyId();
        List<String> empIds = exportServiceContext.getQuery().getEmpIds();
        GeneralDate startDate = exportServiceContext.getQuery().getStartDate();
        GeneralDate endDate = exportServiceContext.getQuery().getEndDate();

        EmpInsReportSettingExport reportSettingExport = exportServiceContext.getQuery().getEmpInsReportSetting();
        EmpInsRptTxtSettingExport rptTxtSettingExport = exportServiceContext.getQuery().getEmpInsRptTxtSetting();

        // Update Setting
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
                rptTxtSettingExport.getOfficeAtr(),
                rptTxtSettingExport.getFdNumber(),
                rptTxtSettingExport.getLineFeedCode()
        );

        reportSettingRepository.update(reportSetting);
        reportTxtSettingRepository.update(reportTxtSetting);

        if(endDate.before(startDate)) {
            throw new BusinessException("Msg_812");
        }

        Map<String, EmpInsHist> empInsHists = empInsHistRepository.getByEmpIdsAndStartDate(empIds, startDate).stream().collect(Collectors.toMap(EmpInsHist::getSid, Function.identity()));
        if (empInsHists.isEmpty()) {
            throw new BusinessException("Msg_51");
        }

        // Pending check マイナンバー印字区分

        List<String> empInsHistEmpIds = empInsHists.values().stream().map(EmpInsHist::getSid).collect(Collectors.toList());
        Map<String, EmpInsGetInfo> empInsGetInfos = empInsGetInfoRepository.getByEmpIds(cid, empInsHistEmpIds).stream().collect(Collectors.toMap(EmpInsGetInfo::getSId, Function.identity()));

        List<String> empInsHistIds = empInsHists.values().stream().map(e -> e.getHistoryItem().get(0).identifier()).collect(Collectors.toList());
        Map<String, EmpInsNumInfo> empInsNumInfos = empInsNumInfoRepository.getByCidAndHistIds(cid, empInsHistIds).stream().collect(Collectors.toMap(EmpInsNumInfo::getHistId, Function.identity()));

        Map<String, EmpInsOffice> empInsOffices = empEstabInsHistRepository.getByHistIdsAndDate(empInsHistIds, endDate).stream().collect(Collectors.toMap(EmpInsOffice::getHistId, Function.identity()));
        List<String> laborOfficeCodes = empInsOffices.values().stream().map(e -> e.getLaborInsCd().v()).collect(Collectors.toList());
        Map<String, LaborInsuranceOffice> laborInsuranceOffices = laborInsOfficeRepository.getByCidAndCodes(cid, laborOfficeCodes).stream().collect(Collectors.toMap(e -> e.getLaborOfficeCode().v(), Function.identity()));
        CompanyInfor companyInfo = companyInforAdapter.getCompanyNotAbolitionByCid(cid);

        Map<String, PublicEmploymentSecurityOffice> pubEmpSecOffices = pubEmpSecOfficeRepository.getByCidAndCodes(cid, laborOfficeCodes).stream().collect(Collectors.toMap(e -> e.getPublicEmploymentSecurityOfficeCode().v(), Function.identity()));

        // dummy param
        LaborContractHist dummyLaborContractHist = new LaborContractHist("", 1, GeneralDate.fromString("2015/01/01", "yyy/MM/dd"), GeneralDate.fromString("2019/01/01", "yyy/MM/dd"));
        ForeignerResHistInfo dummyForResHistInfo = new ForeignerResHistInfo("", 1, 1, GeneralDate.fromString("2015/01/01", "yyy/MM/dd"), GeneralDate.fromString("2019/01/01", "yyy/MM/dd"), "高度専門職", "ベトナム");

        Map<String, EmployeeInfoEx> employeeInfos = employeeInfoAdapter.findBySIds(empInsHistEmpIds).stream().collect(Collectors.toMap(EmployeeInfoEx::getEmployeeId, Function.identity()));
        Map<String, PersonExport> personExports = personExportAdapter.findByPids(employeeInfos.values().stream().map(EmployeeInfoEx::getPId).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(PersonExport::getPersonId, Function.identity()));

        JapaneseEras jpEras = this.jpErasAdapter.getAllEras();

        ExportDataCsv dataExport = ExportDataCsv.builder()
                .empInsHists(empInsHists)
                .reportSetting(reportSetting)
                .reportTxtSetting(reportTxtSetting)
                .empInsNumInfos(empInsNumInfos)
                .empInsOffices(empInsOffices)
                .laborInsuranceOffices(laborInsuranceOffices)
                .companyInfo(companyInfo)
                .pubEmpSecOffices(pubEmpSecOffices)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        generator.generate(exportServiceContext.getGeneratorContext(), dataExport);
    }

    private String toEraNumb(String eraName) {
        switch(eraName) {
            case MEIJI:
                return "1";
            case TAISHO:
                return "2";
            case SHOWA:
                return "3";
            case HEISEI:
                return "4";
            case REIWA:
                return "5";
            default:
                return "";
        }
    }

    private JapaneseDate toJapaneseDate(JapaneseEras jpEras, GeneralDate date) {
        Optional<JapaneseEraName> eraName = jpEras.eraOf(date);
        return eraName.map(japaneseEraName -> new JapaneseDate(date, japaneseEraName)).orElse(null);
    }
}
