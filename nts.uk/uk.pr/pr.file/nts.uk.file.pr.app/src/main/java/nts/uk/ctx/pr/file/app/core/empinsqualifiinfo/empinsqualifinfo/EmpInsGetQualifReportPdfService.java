package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import lombok.val;
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
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.*;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SubNameClass;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
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

    @Inject
    private EmployeeInfoAdapter employeeInfoAdapter;

    @Inject
    private PersonExportAdapter personExportAdapter;

    @Inject
    private JapaneseErasAdapter jpErasAdapter;

    private static final String MEIJI = "明治";
    private static final String TAISHO = "大正";
    private static final String SHOWA = "昭和";
    private static final String HEISEI = "平成";
    private static final String REIWA = "令和";

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

        Map<String, EmpInsHist> empInsHists = empInsHistRepository.getByEmpIdsAndStartDate(empIds, startDate).stream().collect(Collectors.toMap(EmpInsHist::getSid, Function.identity()));
        if (empInsHists.isEmpty()) {
            throw new BusinessException("Msg_51");
        }

        // Pending check マイナンバー印字区分

        List<String> empInsHistEmpIds = empInsHists.values().stream().map(EmpInsHist::getSid).collect(Collectors.toList());
        Map<String, EmpInsGetInfo> empInsGetInfos = empInsGetInfoRepository.getByEmpIds(cid, empInsHistEmpIds).stream().collect(Collectors.toMap(EmpInsGetInfo::getSId, Function.identity()));
        if (empInsGetInfos.isEmpty()) {
            throw new BusinessException("Msg_51");
        }

        List<String> empInsHistIds = empInsHists.values().stream().map(e -> e.getHistoryItem().get(0).identifier()).collect(Collectors.toList());
        Map<String, EmpInsNumInfo> empInsNumInfos = empInsNumInfoRepository.getByCidAndHistIds(cid, empInsHistIds).stream().collect(Collectors.toMap(EmpInsNumInfo::getHistId, Function.identity()));
        if (empInsNumInfos.isEmpty()) {
            throw new BusinessException("Msg_51");
        }

        switch (reportSetting.getOfficeClsAtr()) {
            case OUPUT_LABOR_OFFICE:
                Map<String, EmpInsOffice> empInsOffices = empEstabInsHistRepository.getByHistIdsAndDate(empInsHistIds, endDate).stream().collect(Collectors.toMap(EmpInsOffice::getHistId, Function.identity()));
                List<String> laborOfficeCodes = empInsOffices.values().stream().map(e -> e.getLaborInsCd().v()).collect(Collectors.toList());
                Map<String, LaborInsuranceOffice> laborInsuranceOffices = laborInsOfficeRepository.getByCidAndCodes(cid, laborOfficeCodes).stream().collect(Collectors.toMap(e -> e.getLaborOfficeCode().v(), Function.identity()));
                break;
            case OUTPUT_COMPANY:
                CompanyInfor companyInfo = companyInforAdapter.getCompanyNotAbolitionByCid(cid);
                break;
            default:
                break;
        }

        // dummy param
        LaborContractHist dummyLaborContractHist = new LaborContractHist("", 1, GeneralDate.fromString("2015/01/01", "yyy/MM/dd"), GeneralDate.fromString("2019/01/01", "yyy/MM/dd"));
        ForeignerResHistInfo dummyForResHistInfo = new ForeignerResHistInfo("", 1, 1, GeneralDate.fromString("2015/01/01", "yyy/MM/dd"), GeneralDate.fromString("2019/01/01", "yyy/MM/dd"), "高度専門職", "ベトナム");

        Map<String, EmployeeInfoEx> employeeInfos = employeeInfoAdapter.findBySIds(empInsHistEmpIds).stream().collect(Collectors.toMap(EmployeeInfoEx::getEmployeeId, Function.identity()));
        Map<String, PersonExport> personExports = personExportAdapter.findByPids(employeeInfos.values().stream().map(EmployeeInfoEx::getPId).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(PersonExport::getPersonId, Function.identity()));

        JapaneseEras jpEras = this.jpErasAdapter.getAllEras();

        empIds.forEach(e -> {
            EmpInsGetQualifReport tempReport = new EmpInsGetQualifReport();
            tempReport.setSid(e);
            if (empInsGetInfos.containsKey(e)) {
                // A1_2
                tempReport.setAcquisitionAtr(empInsGetInfos.get(e).getAcquisitionAtr().map(x -> x.value).orElse(null));

                tempReport.setCauseOfInsured(empInsGetInfos.get(e).getInsCauseAtr().map(x -> x.value).orElse(null));

                tempReport.setScheduleWorkingTimePerWeek(empInsGetInfos.get(e).getWorkingTime().map(x -> x.v()).orElse(null));

                tempReport.setEmploymentStatus(empInsGetInfos.get(e).getEmploymentStatus().map(x -> x.value).orElse(null));

                tempReport.setJobPath(empInsGetInfos.get(e).getJobPath().map(x -> x.value).orElse(null));

                tempReport.setPaymentWage(empInsGetInfos.get(e).getPayWage().map(x -> x.v()).orElse(null));

                tempReport.setWagePaymentMode(empInsGetInfos.get(e).getPaymentMode().map(x -> x.value).orElse(null));

                tempReport.setOccupation(empInsGetInfos.get(e).getJobAtr().map(x -> x.value).orElse(null));
            }
            if (empInsHists.containsKey(e)) {
                val histId = empInsHists.get(e).getHistoryItem().get(0).identifier();
                if (empInsNumInfos.containsKey(histId)) {
                    // A1_1
                    tempReport.setInsuredNumber(Integer.valueOf(empInsNumInfos.get(histId).getEmpInsNumber().v()));
                }
            }
            if(employeeInfos.containsKey(e)) {
                val pId = employeeInfos.get(e).getPId();
                if (personExports.containsKey(pId)) {
                    if (reportSettingExport.getSubmitNameAtr() == SubNameClass.PERSONAL_NAME.value && tempReport.getAcquisitionAtr() == AcquisitionAtr.NEW.value) {
                        // A1_3
                        tempReport.setInsuredName(personExports.get(pId).getPersonNameGroup().getPersonName().getFullName());
                        // A1_4
                        tempReport.setNameOfInsuredPeople(personExports.get(pId).getPersonNameGroup().getPersonName().getFullNameKana());
                    }
                    if (reportSettingExport.getSubmitNameAtr() == SubNameClass.REPORTED_NAME.value && tempReport.getAcquisitionAtr() == AcquisitionAtr.NEW.value) {
                        // A1_3
                        tempReport.setInsuredName(personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullName());
                        // A1_4
                        tempReport.setNameOfInsuredPeople(personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullNameKana());
                    }
                    if (reportSettingExport.getNameChangeClsAtr() == PrinfCtg.PRINT.value && tempReport.getAcquisitionAtr() == AcquisitionAtr.REHIRE.value && reportSettingExport.getSubmitNameAtr() == SubNameClass.PERSONAL_NAME.value) {
                        // A1_3
                        tempReport.setInsuredName(personExports.get(pId).getPersonNameGroup().getOldName().getFullName());
                        // A1_4
                        tempReport.setNameOfInsuredPeople(personExports.get(pId).getPersonNameGroup().getOldName().getFullNameKana());
                        // A1_5
                        tempReport.setNameAfterChange(personExports.get(pId).getPersonNameGroup().getPersonName().getFullName());
                        // A1_6
                        tempReport.setFullNameAfterChange(personExports.get(pId).getPersonNameGroup().getPersonName().getFullNameKana());
                    }
                    if (reportSettingExport.getNameChangeClsAtr() == PrinfCtg.PRINT.value && tempReport.getAcquisitionAtr() == AcquisitionAtr.REHIRE.value && reportSettingExport.getSubmitNameAtr() == SubNameClass.REPORTED_NAME.value) {
                        // A1_3
                        tempReport.setInsuredName(personExports.get(pId).getPersonNameGroup().getOldName().getFullName());
                        // A1_4
                        tempReport.setNameOfInsuredPeople(personExports.get(pId).getPersonNameGroup().getOldName().getFullNameKana());
                        // A1_5
                        tempReport.setNameAfterChange(personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullName());
                        // A1_6
                        tempReport.setFullNameAfterChange(personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullNameKana());
                    }
                    // A1_7
                    tempReport.setGender(personExports.get(pId).getGender());

                    val birthDate = personExports.get(pId).getBirthDate();
                    val birthDateJp = toJapaneseDate(jpEras, birthDate);
                    val eraNumb = toEraNumb(birthDateJp.era());
                    // A1_8
                    tempReport.setEraDateOfBirth(eraNumb);
                    // A1_9
                    tempReport.setDateOfBirthJp(birthDateJp.toFullDateInt() + "");
                }
            }
        });

        generator.generate(exportServiceContext.getGeneratorContext(), listDataExport);
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
