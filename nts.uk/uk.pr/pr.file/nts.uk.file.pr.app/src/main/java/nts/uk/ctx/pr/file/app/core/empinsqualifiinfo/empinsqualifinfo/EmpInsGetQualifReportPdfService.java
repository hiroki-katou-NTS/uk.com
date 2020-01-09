package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.primitive.PrimitiveValueBase;
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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static final int DATE_OF_BIRTH = 0;

    private static final int OTHER_DATE = 1;

    private static final Map<Integer, Integer> mapPrintAtr = Stream.of(new Integer[][] {
            { 0, 2 },
            { 1, 1 }
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private static final Map<Integer, Integer> mapAcquisitionAtr = Stream.of(new Integer[][] {
            { 0, 1 },
            { 1, 2 }
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private static final Map<Integer, Integer> mapInsuredCause = Stream.of(new Integer[][] {
            { 0, 1 },
            { 1, 2 },
            { 2, 3 },
            { 3, 4 },
            { 4, 8 },
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private static final Map<Integer, Integer> mapPaymentMode = Stream.of(new Integer[][] {
            { 0, 1 },
            { 1, 2 },
            { 2, 3 },
            { 3, 4 },
            { 4, 5 },
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private static final Map<Integer, Integer> mapEmploymentStatus = Stream.of(new Integer[][] {
            { 0, 1 },
            { 1, 2 },
            { 2, 3 },
            { 3, 4 },
            { 4, 5 },
            { 5, 6 },
            { 6, 7 }
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private static final Map<Integer, String> mapJobAtr = Stream.of(new Object[][] {
            { 0, "01" },
            { 1, "02" },
            { 2, "03" },
            { 3, "04" },
            { 4, "05" },
            { 5, "06" },
            { 6, "07" },
            { 7, "08" },
            { 8, "09" },
            { 9, "10" },
            { 10, "11" }
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

    private static final Map<Integer, Integer> mapJobPath = Stream.of(new Integer[][] {
            { 0, 1 },
            { 1, 2 },
            { 2, 3 },
            { 3, 4 }
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

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

        if (endDate.before(startDate)) {
            throw new BusinessException("Msg_812");
        }

        List<EmpInsGetQualifReport> listDataExport = new ArrayList<>();

        Map<String, EmpInsHist> empInsHists = empInsHistRepository.getByEmpIdsAndDate(cid, empIds, startDate, endDate).stream().collect(Collectors.toMap(EmpInsHist::getSid, Function.identity()));
        if (empInsHists.isEmpty()) {
            throw new BusinessException("MsgQ_51");
        }
        List<String> insHistEmpIds = new ArrayList<>(empInsHists.keySet());

        // Pending check マイナンバー印字区分

        Map<String, EmpInsGetInfo> empInsGetInfos = empInsGetInfoRepository.getByEmpIds(cid, insHistEmpIds).stream().collect(Collectors.toMap(EmpInsGetInfo::getSId, Function.identity()));

        List<String> empInsHistIds = empInsHists.values().stream().map(e -> e.getHistoryItem().get(0).identifier()).collect(Collectors.toList());
        Map<String, EmpInsNumInfo> empInsNumInfos = empInsNumInfoRepository.getByCidAndHistIds(cid, empInsHistIds).stream().collect(Collectors.toMap(EmpInsNumInfo::getHistId, Function.identity()));

        Map<String, EmpInsOffice> empInsOffices = empEstabInsHistRepository.getByHistIdsAndStartDateInPeriod(empInsHistIds, startDate, endDate).stream().collect(Collectors.toMap(EmpInsOffice::getHistId, Function.identity()));
        List<String> laborOfficeCodes = empInsOffices.values().stream().map(e -> e.getLaborInsCd().v()).collect(Collectors.toList());
        Map<String, LaborInsuranceOffice> laborInsuranceOffices = laborInsOfficeRepository.getByCidAndCodes(cid, laborOfficeCodes).stream().collect(Collectors.toMap(e -> e.getLaborOfficeCode().v(), Function.identity()));
        CompanyInfor companyInfo = companyInforAdapter.getCompanyNotAbolitionByCid(cid);

        // dummy param
        LaborContractHist dummyLaborContractHist = new LaborContractHist("", 1,
                GeneralDate.fromString("2015/01/01", "yyy/MM/dd"),
                GeneralDate.fromString("2019/01/01", "yyy/MM/dd"));
        ForeignerResHistInfo dummyForResHistInfo = new ForeignerResHistInfo("", 1, 1,
                GeneralDate.fromString("2015/01/01", "yyy/MM/dd"),
                GeneralDate.fromString("2019/01/01", "yyy/MM/dd"),
                "技能", "14", "ベトナム", "704");

        Map<String, EmployeeInfoEx> employeeInfos = employeeInfoAdapter.findBySIds(insHistEmpIds).stream().collect(Collectors.toMap(EmployeeInfoEx::getEmployeeId, Function.identity()));
        Map<String, PersonExport> personExports = personExportAdapter.findByPids(employeeInfos.values().stream().map(EmployeeInfoEx::getPId).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(PersonExport::getPersonId, Function.identity()));

        JapaneseEras jpEras = this.jpErasAdapter.getAllEras();

        insHistEmpIds.forEach(e -> {
            EmpInsGetQualifReport tempReport = new EmpInsGetQualifReport();
            tempReport.setSid(e);
            tempReport.setScd(employeeInfos.containsKey(e) ? employeeInfos.get(e).getEmployeeCode() : "");
            if (empInsGetInfos.containsKey(e)) {
                // A1_2
                tempReport.setAcquisitionAtr(mapAcquisitionAtr.get(empInsGetInfos.get(e).getAcquisitionAtr().map(x -> x.value).orElse(null)));
                // A1_11
                tempReport.setCauseOfInsured(mapInsuredCause.get(empInsGetInfos.get(e).getInsCauseAtr().map(x -> x.value).orElse(null)));
                // A1_18
                tempReport.setScheduleWorkingTimePerWeek(empInsGetInfos.get(e).getWorkingTime().map(PrimitiveValueBase::v).orElse(null));
                // A1_15
                tempReport.setEmploymentStatus(mapEmploymentStatus.get(empInsGetInfos.get(e).getEmploymentStatus().map(x -> x.value).orElse(null)));
                // A1_17
                tempReport.setJobPath(mapJobPath.get(empInsGetInfos.get(e).getJobPath().map(x -> x.value).orElse(null)));
                // A1_13
                tempReport.setPaymentWage(empInsGetInfos.get(e).getPayWage().map(PrimitiveValueBase::v).orElse(null));
                // A1_12
                tempReport.setWagePaymentMode(mapPaymentMode.get(empInsGetInfos.get(e).getPaymentMode().map(x -> x.value).orElse(null)));
                // A1_16
                tempReport.setOccupation(mapJobAtr.get(empInsGetInfos.get(e).getJobAtr().map(x -> x.value).orElse(null)));
                // A1_19
                tempReport.setSetContractPeriod(mapPrintAtr.get(empInsGetInfos.get(e).getPrintAtr().map(x -> x.value).orElse(null)));
            }
            if (empInsHists.containsKey(e)) {
                val histId = empInsHists.get(e).getHistoryItem().get(0).identifier();
                if (empInsNumInfos.containsKey(histId)) {
                    // A1_1
                    tempReport.setInsuredNumber(empInsNumInfos.get(histId).getEmpInsNumber().v());

                    String laborCode = empInsOffices.containsKey(histId) ? empInsOffices.get(histId).getLaborInsCd().v() : "";

                    // A1_10
                    if (laborInsuranceOffices.containsKey(laborCode)) {
                        tempReport.setOfficeNumber1(laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeNumber1().map(PrimitiveValueBase::v).orElse(""));
                        tempReport.setOfficeNumber2(laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeNumber2().map(PrimitiveValueBase::v).orElse(""));
                        tempReport.setOfficeNumber3(laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeNumber3().map(PrimitiveValueBase::v).orElse(""));
                    }
                    if (reportSettingExport.getOfficeClsAtr() == OfficeCls.OUTPUT_COMPANY.value) {
                        // A1_23
                        tempReport.setOfficeName(companyInfo.getCompanyName());
                        // A3_1
                        tempReport.setOfficePostalCode(companyInfo.getPostCd());
                        // A3_2
                        tempReport.setOfficeLocation_1(companyInfo.getAdd_1());
                        tempReport.setOfficeLocation_2(companyInfo.getAdd_2());
                        // A3_3
                        tempReport.setBusinessOwnerName(companyInfo.getRepname());
                        // A3_4
                        tempReport.setOfficePhoneNumber(companyInfo.getPhoneNum());
                    } else if (reportSettingExport.getOfficeClsAtr() == OfficeCls.OUPUT_LABOR_OFFICE.value && laborInsuranceOffices.containsKey(laborCode)) {
                        // A1_23
                        tempReport.setOfficeName(laborInsuranceOffices.get(laborCode).getLaborOfficeName().v());
                        // A3_1
                        tempReport.setOfficePostalCode(laborInsuranceOffices.get(laborCode).getBasicInformation().getStreetAddress().getPostalCode().map(PrimitiveValueBase::v).orElse(""));

                        val streetAddress = laborInsuranceOffices.get(laborCode).getBasicInformation().getStreetAddress();
                        val address1 = streetAddress.getAddress1().map(PrimitiveValueBase::v).orElse("");
                        val address2 = streetAddress.getAddress2().map(PrimitiveValueBase::v).orElse("");
                        // A3_2
                        tempReport.setOfficeLocation_1(address1);
                        tempReport.setOfficeLocation_2(address2);
                        // A3_3
                        tempReport.setBusinessOwnerName(laborInsuranceOffices.get(laborCode).getBasicInformation().getRepresentativeName().map(PrimitiveValueBase::v).orElse(""));
                        // A3_4
                        tempReport.setOfficePhoneNumber(laborInsuranceOffices.get(laborCode).getBasicInformation().getStreetAddress().getPhoneNumber().map(PrimitiveValueBase::v).orElse(""));
                    }
                    val qualificationDate = empInsHists.get(e).getHistoryItem().get(0).start();
                    val qualificationDateJp = toJapaneseDate(jpEras, qualificationDate);
                    // A1_14
                    tempReport.setQualificationDateJp(toEraDate(qualificationDateJp, OTHER_DATE));
                }
            }
            if (employeeInfos.containsKey(e)) {
                val pId = employeeInfos.get(e).getPId();
                if (personExports.containsKey(pId)) {
                    if (reportSettingExport.getSubmitNameAtr() == SubNameClass.PERSONAL_NAME.value) {
                        // A1_3
                        tempReport.setInsuredName(personExports.get(pId).getPersonNameGroup().getPersonName().getFullName());
                        // A1_4
                        tempReport.setInsuredFullName(personExports.get(pId).getPersonNameGroup().getPersonName().getFullNameKana());
                    }
                    if (reportSettingExport.getSubmitNameAtr() == SubNameClass.REPORTED_NAME.value) {
                        // A1_3
                        tempReport.setInsuredName(personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullName());
                        // A1_4
                        tempReport.setInsuredFullName(personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullNameKana());
                    }
                    if (reportSettingExport.getNameChangeClsAtr() == PrinfCtg.PRINT.value
                            && tempReport.getAcquisitionAtr() != null
                            && (tempReport.getAcquisitionAtr() - 1) == AcquisitionAtr.REHIRE.value
                            && reportSettingExport.getSubmitNameAtr() == SubNameClass.PERSONAL_NAME.value) {
                        // A1_3
                        tempReport.setInsuredName(personExports.get(pId).getPersonNameGroup().getOldName().getFullName());
                        // A1_4
                        tempReport.setInsuredFullName(personExports.get(pId).getPersonNameGroup().getOldName().getFullNameKana());
                        // A1_5
                        tempReport.setNameAfterChange(personExports.get(pId).getPersonNameGroup().getPersonName().getFullName());
                        // A1_6
                        tempReport.setFullNameAfterChange(personExports.get(pId).getPersonNameGroup().getPersonName().getFullNameKana());
                    }
                    if (reportSettingExport.getNameChangeClsAtr() == PrinfCtg.PRINT.value
                            && tempReport.getAcquisitionAtr() != null
                            && (tempReport.getAcquisitionAtr() - 1) == AcquisitionAtr.REHIRE.value
                            && reportSettingExport.getSubmitNameAtr() == SubNameClass.REPORTED_NAME.value) {
                        // A1_3
                        tempReport.setInsuredName(personExports.get(pId).getPersonNameGroup().getOldName().getFullName());
                        // A1_4
                        tempReport.setInsuredFullName(personExports.get(pId).getPersonNameGroup().getOldName().getFullNameKana());
                        // A1_5
                        tempReport.setNameAfterChange(personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullName());
                        // A1_6
                        tempReport.setFullNameAfterChange(personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullNameKana());
                    }
                    // A1_7
                    tempReport.setGender(personExports.get(pId).getGender());

                    val birthDate = personExports.get(pId).getBirthDate();
                    val birthDateJp = toJapaneseDate(jpEras, birthDate);
                    val eraNumb = toEraNumber(birthDateJp.era(), DATE_OF_BIRTH);
                    // A1_8
                    tempReport.setEraDateOfBirth(eraNumb);
                    // A1_9
                    tempReport.setDateOfBirthJp(toEraDate(birthDateJp, DATE_OF_BIRTH).substring(1));
                    String insuredRomanName = personExports.get(pId).getPersonNameGroup().getPersonRomanji().getFullName();
                    if (insuredRomanName.length() <= 28) {
                        // A2_1
                        tempReport.setInsuredRomanName(insuredRomanName);
                    } else {
                        // A2_1
                        tempReport.setInsuredRomanName(insuredRomanName.substring(0, 28));
                        // A2_2
                        tempReport.setInsuredRomanName2(insuredRomanName.substring(28));
                    }
                    // to sort
                    tempReport.setPersonalNameKana(personExports.get(pId).getPersonNameGroup().getPersonName().getFullNameKana());
                }
            }

            // dummy
            {
                // TODO check existence of data
                if (tempReport.getSetContractPeriod() != null && tempReport.getSetContractPeriod() == ContractPeriodPrintAtr.PRINT.value) {
                    val contractStartDateJp = toJapaneseDate(jpEras, dummyLaborContractHist.getStartDate());
                    // A1_20
                    tempReport.setContractStartDateJp(toEraDate(contractStartDateJp, OTHER_DATE));

                    val contractEndDateJp = toJapaneseDate(jpEras, dummyLaborContractHist.getEndDate());
                    // A1_21
                    tempReport.setContractEndDateJp(toEraDate(contractEndDateJp, OTHER_DATE));

                    // A1_22
                    tempReport.setContractRenewalProvision(dummyLaborContractHist.getContractRenewalProvision());
                }

                // A1_24 pending

                // A2_3
                tempReport.setNationalityRegion(dummyForResHistInfo.getNationalityCode());
                // A2_4
                tempReport.setResidenceStatus(dummyForResHistInfo.getResidenceStatusCode());
                // A2_5
                tempReport.setStayPeriod(dummyForResHistInfo.getEndDate().toString("yyyy/MM/dd").replace("/", ""));
                // A2_6
                tempReport.setUnqualifiedActivityPermission(dummyForResHistInfo.getUnqualifiedActivityPermission());
                // A2_7
                tempReport.setContractWorkAtr(dummyForResHistInfo.getContractWorkAtr());

            }
            // A3_5
            tempReport.setSubmissionDateJp(toJapaneseDate(jpEras, fillingDate));
            listDataExport.add(tempReport);
        });

        switch (reportSetting.getOutputOrderAtr()) {
            case INSURANCE_NUMBER:
                listDataExport.sort(Comparator.comparing(EmpInsGetQualifReport::getInsuredNumber, Comparator.nullsFirst(Comparator.naturalOrder()))
                        .thenComparing(EmpInsGetQualifReport::getScd, Comparator.nullsFirst(Comparator.naturalOrder())));
                break;
            case DEPARTMENT_EMPLOYEE:
            case EMPLOYEE_CODE:
                listDataExport.sort(Comparator.comparing(EmpInsGetQualifReport::getScd, Comparator.nullsFirst(Comparator.naturalOrder())));
                break;
            case EMPLOYEE:
                listDataExport.sort(Comparator.comparing(EmpInsGetQualifReport::getPersonalNameKana, Comparator.nullsFirst(Comparator.naturalOrder()))
                        .thenComparing(EmpInsGetQualifReport::getScd, Comparator.nullsFirst(Comparator.naturalOrder())));
                break;
            default:
                break;
        }

        generator.generate(exportServiceContext.getGeneratorContext(), listDataExport);
    }

    private String toEraNumber(String eraName, int dateType) {
        switch (eraName) {
            case TAISHO:
                return dateType == DATE_OF_BIRTH ? "2" : " ";
            case SHOWA:
                return dateType == DATE_OF_BIRTH ? "3" : " ";
            case HEISEI:
                return "4";
            case REIWA:
                return "5";
            default:
                return " ";
        }
    }

    private JapaneseDate toJapaneseDate(JapaneseEras jpEras, GeneralDate date) {
        Optional<JapaneseEraName> eraName = jpEras.eraOf(date);
        return eraName.map(japaneseEraName -> new JapaneseDate(date, japaneseEraName)).orElse(null);
    }

    private String toEraDate(JapaneseDate date, int dateType) {
        return toEraNumber(date.era(), dateType) + ((date.year() + 1) < 10 ? "0" + (date.toFullDateInt() + 10000) : (date.toFullDateInt() + 10000));
    }
}
