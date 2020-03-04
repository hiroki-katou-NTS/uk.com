package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInforAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExportAdapter;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformation;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformationRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.OutputFormatClass;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCrSetRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurOutOrder;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class GuaByTheInsurExportService extends ExportService<GuaByTheInsurExportQuery> {

    @Inject
    private GuaByTheInsurExportExcelGenerator generatorExcel;

    @Inject
    private GuaByTheInsurExportCSVGenerator generatorCsv;

    @Inject
    private GuaByTheInsurExportRepository repo;

    @Inject
    private SocialInsurancePrefectureInformationRepository prefectureInformation;

    @Inject
    private EmpWelfarePenInsQualiInforRepository mEmpWelfarePenInsQualiInforRepository;

    @Inject
    private SocialInsurNotiCrSetRepository mSocialInsurNotiCrSetRepository;

    @Inject
    private EmplHealInsurQualifiInforRepository mEmplHealInsurQualifiInforRepository;

    @Inject
    private GuaByTheInsurExportRepository guaByTheInsurExportRepository;

    @Inject
    private EmployeeInfoAdapter employeeInfoAdapter;

    @Inject
    private PersonExportAdapter personExportAdapter;

    @Inject
    private CompanyInforAdapter companyInforAdapter;


    @Override
    protected void handle(ExportServiceContext<GuaByTheInsurExportQuery> exportServiceContext) {
        final int TYPE_EXPORT_EXCEL_FILE = 0;
        String userId = AppContexts.user().userId();
        String cid = AppContexts.user().companyId();
        List<PensionOfficeDataExport> pension = new ArrayList<>();
        List<PensionOfficeDataExport> healthInsAss = new ArrayList<>();
        List<EmpPenFundSubData> empPensionFund = new ArrayList<>();
        CompanyInfor company = companyInforAdapter.getCompanyNotAbolitionByCid(AppContexts.user().companyId());
        List<SocialInsurancePrefectureInformation> infor = prefectureInformation.findByHistory();
        SocialInsurNotiCreateSet ins = new SocialInsurNotiCreateSet(userId, cid,
                exportServiceContext.getQuery().getSocialInsurNotiCreateSetQuery().getOfficeInformation(),
                exportServiceContext.getQuery().getSocialInsurNotiCreateSetQuery().getBusinessArrSymbol(),
                exportServiceContext.getQuery().getSocialInsurNotiCreateSetQuery().getOutputOrder(),
                exportServiceContext.getQuery().getSocialInsurNotiCreateSetQuery().getPrintPersonNumber(),
                exportServiceContext.getQuery().getSocialInsurNotiCreateSetQuery().getSubmittedName(),
                exportServiceContext.getQuery().getSocialInsurNotiCreateSetQuery().getInsuredNumber(),
                exportServiceContext.getQuery().getSocialInsurNotiCreateSetQuery().getFdNumber(),
                exportServiceContext.getQuery().getSocialInsurNotiCreateSetQuery().getTextPersonNumber(),
                exportServiceContext.getQuery().getSocialInsurNotiCreateSetQuery().getOutputFormat(),
                exportServiceContext.getQuery().getSocialInsurNotiCreateSetQuery().getLineFeedCode()
        );
        mSocialInsurNotiCrSetRepository.update(ins);
        if (exportServiceContext.getQuery().getTypeExport() == TYPE_EXPORT_EXCEL_FILE) {
            checkAcquiNotiInsurProcess(exportServiceContext.getQuery().getEmpIds(), exportServiceContext.getQuery().getStartDate(), exportServiceContext.getQuery().getEndDate());
        }
        if (exportServiceContext.getQuery().getTypeExport() != TYPE_EXPORT_EXCEL_FILE) {
            reportTextOutputCheck(exportServiceContext.getQuery().getEmpIds(), exportServiceContext.getQuery().getStartDate(), exportServiceContext.getQuery().getEndDate());
            if(ins.getOutputFormat().get() == OutputFormatClass.PEN_OFFICE) {
                pension = guaByTheInsurExportRepository.getDataExportCSV(exportServiceContext.getQuery().getEmpIds(), cid,
                        exportServiceContext.getQuery().getStartDate(), exportServiceContext.getQuery().getEndDate());
                if(pension.isEmpty()){
                    throw new BusinessException("Msg_37");
                }
                List<String> emplds = pension.stream().map(PensionOfficeDataExport::getSid).collect(Collectors.toList());
                List<EmployeeInfoEx> employeeInfoList = employeeInfoAdapter.findBySIds(emplds);
                List<PersonExport> personList = personExportAdapter.findByPids(employeeInfoList.stream().map(EmployeeInfoEx::getPId).collect(Collectors.toList()));
                pension.forEach(item -> {
                    PersonExport p = getPersonInfor(employeeInfoList, personList, item.getSid());
                    item.setPersonName(p.getPersonNameGroup().getPersonName().getFullName());
                    item.setPersonNameKana(p.getPersonNameGroup().getPersonName().getFullNameKana());
                    item.setOldName(p.getPersonNameGroup().getTodokedeFullName().getFullName());
                    item.setOldNameKana(p.getPersonNameGroup().getTodokedeFullName().getFullNameKana());
                    item.setBirthDay(p.getBirthDate().toString("yyyy-MM-dd"));
                    item.setGender(p.getGender());
                });
            }
            if(ins.getOutputFormat().get() == OutputFormatClass.HEAL_INSUR_ASSO) {
                healthInsAss = guaByTheInsurExportRepository.getDataHealthInsAss(exportServiceContext.getQuery().getEmpIds(), cid, userId,
                        exportServiceContext.getQuery().getStartDate(), exportServiceContext.getQuery().getEndDate());
                if(healthInsAss.isEmpty()){
                    throw new BusinessException("Msg_37");
                }
                List<String> emplds = healthInsAss.stream().map(PensionOfficeDataExport::getSid).collect(Collectors.toList());
                List<EmployeeInfoEx> employeeInfoList = employeeInfoAdapter.findBySIds(emplds);
                List<PersonExport> personList = personExportAdapter.findByPids(employeeInfoList.stream().map(EmployeeInfoEx::getPId).collect(Collectors.toList()));
                healthInsAss.forEach(item -> {
                    PersonExport p = getPersonInfor(employeeInfoList, personList, item.getSid());
                    item.setPersonName(p.getPersonNameGroup().getPersonName().getFullName());
                    item.setPersonNameKana(p.getPersonNameGroup().getPersonName().getFullNameKana());
                    item.setOldNameKana(p.getPersonNameGroup().getTodokedeFullName().getFullNameKana());
                    item.setOldName(p.getPersonNameGroup().getTodokedeFullName().getFullName());
                    item.setBirthDay(p.getBirthDate().toString("yyyy-MM-dd"));
                    item.setGender(p.getGender());
                });
            }
            if(ins.getOutputFormat().get() == OutputFormatClass.THE_WELF_PEN) {
                empPensionFund = guaByTheInsurExportRepository.getDataEmpPensionFund(exportServiceContext.getQuery().getEmpIds(), cid,
                        exportServiceContext.getQuery().getStartDate(), exportServiceContext.getQuery().getEndDate());
                if(empPensionFund.isEmpty()){
                    throw new BusinessException("Msg_37");
                }
                List<String> emplds = empPensionFund.stream().map(EmpPenFundSubData::getSid).collect(Collectors.toList());
                List<EmployeeInfoEx> employeeInfoList = employeeInfoAdapter.findBySIds(emplds);
                List<PersonExport> personList = personExportAdapter.findByPids(employeeInfoList.stream().map(EmployeeInfoEx::getPId).collect(Collectors.toList()));
                empPensionFund.forEach(item -> {
                    PersonExport p = getPersonInfor(employeeInfoList, personList, item.getSid());
                    item.setGender(p.getGender());
                    item.setPersonName(p.getPersonNameGroup().getPersonName().getFullName());
                    item.setPersonNameKana(p.getPersonNameGroup().getPersonName().getFullNameKana());
                    item.setOldNameKana(p.getPersonNameGroup().getTodokedeFullName().getFullNameKana());
                    item.setOldName(p.getPersonNameGroup().getTodokedeFullName().getFullName());
                    item.setBirthDay(p.getBirthDate().toString("yyyy-MM-dd"));
                });
            }
        }
        List<GuaByTheInsurExportDto> dtoList = printInsuredQualifiNoti(exportServiceContext.getQuery().getEmpIds(),
                exportServiceContext.getQuery().getStartDate(), exportServiceContext.getQuery().getEndDate(),company);
        if(ins.getOutputOrder() == SocialInsurOutOrder.EMPLOYEE_KANA_ORDER) {
            dtoList = orderBy(dtoList);
        }
        ExportDataCsv exportData = ExportDataCsv.builder()
                .listContent(dtoList)
                .ins(ins)
                .infor(infor)
                .company(company)
                .empPenFundSub(empPensionFund)
                .pensionOfficeData(pension.isEmpty() ? healthInsAss : pension)
                .baseDate(exportServiceContext.getQuery().getBaseDate())
                .startDate(exportServiceContext.getQuery().getStartDate())
                .endDate(exportServiceContext.getQuery().getEndDate())
                .build();

        if (exportServiceContext.getQuery().getTypeExport() == TYPE_EXPORT_EXCEL_FILE) {
            generatorExcel.generate(exportServiceContext.getGeneratorContext(), exportData);
        }
        if (exportServiceContext.getQuery().getTypeExport() != TYPE_EXPORT_EXCEL_FILE) {
            generatorCsv.generate(exportServiceContext.getGeneratorContext(), exportData);
        }
    }

    private PersonExport getPersonInfor(List<EmployeeInfoEx> employeeInfoList, List<PersonExport> personList, String empId){
        PersonExport person = new PersonExport();
        Optional<EmployeeInfoEx> employeeInfoEx = employeeInfoList.stream().filter(item -> item.getEmployeeId().equals(empId)).findFirst();
        if(employeeInfoEx.isPresent()) {
            Optional<PersonExport> personEx = personList.stream().filter(item -> item.getPersonId().equals(employeeInfoEx.get().getPId())).findFirst();
            if (personEx.isPresent()){
                person.setPersonId(personEx.get().getPersonId());
                person.setPersonNameGroup(personEx.get().getPersonNameGroup());
                person.setBirthDate(personEx.get().getBirthDate());
                person.setGender(personEx.get().getGender());
            }
        }
        return person;
    }

    private List<GuaByTheInsurExportDto> orderBy(List<GuaByTheInsurExportDto> dtoList){
        return dtoList.stream().sorted(Comparator.comparing(GuaByTheInsurExportDto::getOfficeCd).thenComparing(GuaByTheInsurExportDto::getNameOfInsuredPerson1)).collect(Collectors.toList());
    }

    private List<GuaByTheInsurExportDto> printInsuredQualifiNoti(List<String> employeeIds, GeneralDate startDate, GeneralDate endDate, CompanyInfor company) {
        return insurQualiNotiProcess(employeeIds, startDate, endDate, company);
    }

    private void checkAcquiNotiInsurProcess(List<String> employeeIds, GeneralDate startDate, GeneralDate endDate) {
        if (startDate.after(endDate)) {
            throw new BusinessException("Msg_812");
        }
        boolean checkHealInsur = mEmplHealInsurQualifiInforRepository.checkEmplHealInsurQualifiInforStartDate(startDate, endDate, employeeIds);
        boolean checkWelfarePen = mEmpWelfarePenInsQualiInforRepository.checkEmpWelfarePenInsQualiInforStart(startDate, endDate, employeeIds);
        if (checkHealInsur && checkWelfarePen) {
            throw new BusinessException("Msg_37");
        }

    }

    private void getPersonInfor(List<EmployeeInfoEx> employeeInfoList, List<PersonExport> personList, GuaByTheInsurExportDto data, String empId, int personName){
        Optional<EmployeeInfoEx> employeeInfoEx = employeeInfoList.stream().filter(item -> item.getEmployeeId().equals(empId)).findFirst();
        if(employeeInfoEx.isPresent()) {
            data.setEmployeeCd(employeeInfoEx.get().getEmployeeCode());
            Optional<PersonExport> person = personList.stream().filter(item -> item.getPersonId().equals(employeeInfoEx.get().getPId())).findFirst();
            if(person.isPresent() && personName == 0){
                data.setNameOfInsuredPersonMr(person.get().getPersonNameGroup().getPersonName().getFullName());
                data.setNameOfInsuredPerson(person.get().getPersonNameGroup().getPersonName().getFullName());
                data.setNameOfInsuredPersonMrK(person.get().getPersonNameGroup().getPersonName().getFullNameKana());
                data.setNameOfInsuredPerson1(person.get().getPersonNameGroup().getPersonName().getFullNameKana());
            }
            if(person.isPresent() && personName == 1){
                data.setNameOfInsuredPersonMr(person.get().getPersonNameGroup().getTodokedeFullName().getFullName());
                data.setNameOfInsuredPerson(person.get().getPersonNameGroup().getTodokedeFullName().getFullName());
                data.setNameOfInsuredPersonMrK(person.get().getPersonNameGroup().getTodokedeFullName().getFullNameKana());
                data.setNameOfInsuredPerson1(person.get().getPersonNameGroup().getTodokedeFullName().getFullNameKana());
            }
            if(person.isPresent()) {
                data.setBrithDay(person.get().getBirthDate().toString("yyyy-MM-dd"));
                data.setGender(person.get().getGender());
            }
        }
    }

    private List<GuaByTheInsurExportDto> insurQualiNotiProcess(List<String> employeeIds, GeneralDate startDate, GeneralDate endDate, CompanyInfor company) {
        final int Enum_BusinessDivision_OUTPUT_COMPANY_NAME = 0;
        final int Enum_BusinessDivision_OUTPUT_SIC_INSURES = 1;
        final int Enum_BussEsimateClass_HEAL_INSUR_OFF_ARR_SYMBOL = 0;
        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();

        List<GuaByTheInsurExportDto> data = new ArrayList<>();
        List<Object[]> dataExport = repo.getDataExport(employeeIds,cid,uid,startDate,endDate);
        if(dataExport == null || dataExport.isEmpty()) {
            throw new BusinessException("Msg_37");
        }
        List<String> emplds = dataExport.stream().map(i -> i[38].toString()).collect(Collectors.toList());
        List<EmployeeInfoEx> employeeInfoList = employeeInfoAdapter.findBySIds(emplds);
        List<PersonExport> personList = personExportAdapter.findByPids(employeeInfoList.stream().map(i -> i.getPId()).collect(Collectors.toList()));

        dataExport.forEach(element -> {
            GuaByTheInsurExportDto temp = new GuaByTheInsurExportDto();

            //C1_10
            temp.setFilingDate(startDate);
            switch (Integer.valueOf(element[0].toString())){
                case Enum_BusinessDivision_OUTPUT_COMPANY_NAME :{
                    //C1_4
                    temp.setOfficePostalCode(company.getPostCd() != null ? company.getPostCd() : "");
                    //C1_5
                    temp.setOfficeAddress1(company.getAdd_1() !=null  ? company.getAdd_1() : "");
                    //C1_6
                    temp.setOfficeAddress2(company.getAdd_2() !=null  ? company.getAdd_2()  : "");
                    //C1_7
                    temp.setBusinessName(company.getCompanyName() !=null  ? company.getCompanyName()  : "");
                    //C1_8
                    temp.setBusinessName1(company.getRepname() !=null  ? company.getRepname() : "");
                    //C1_9
                    temp.setPhoneNumber(company.getPhoneNum() !=null  ? company.getPhoneNum() : "");
                    break;
                }
                case Enum_BusinessDivision_OUTPUT_SIC_INSURES:{
                    //set data to file output
                    //C1_4
                    temp.setOfficePostalCode(element[5] != null ? element[5].toString() : "");
                    //C1_5
                    temp.setOfficeAddress1(element[2] != null ? element[2].toString() : "");
                    //C1_6
                    temp.setOfficeAddress2(element[3] != null ? element[3].toString() : "");
                    //C1_7
                    temp.setBusinessName(element[1] != null ? element[1].toString() : "");
                    //C1_8
                    temp.setBusinessName1(element[6] != null ? element[6].toString() : "");
                    //C1_9
                    temp.setPhoneNumber(element[4] != null ? element[4].toString() : "");
                    break;
                }
                default:{
                    //set data to file output
                    //C1_4
                    temp.setOfficePostalCode("");
                    //C1_5
                    temp.setOfficeAddress1("");
                    //C1_6
                    temp.setOfficeAddress2("");
                    //C1_7
                    temp.setBusinessName("");
                    //C1_8
                    temp.setBusinessName1("");
                    //C1_9
                    temp.setPhoneNumber("");
                    //C2_28
                    temp.setStreetAddress("");
                    //C2_29
                    temp.setAddressKana("");
                }

            }
            if(Integer.valueOf(element[8].toString()) == Enum_BussEsimateClass_HEAL_INSUR_OFF_ARR_SYMBOL){
                //C1_1
                temp.setBusinessstablishmentbCode1(element[9] != null ? element[9].toString() : "");
                //C1_2
                temp.setBusinessstablishmentbCode2(element[10] != null ? element[10].toString() : "");
                //C1_3
                temp.setOfficeNumber(element[35] != null ? element[35].toString() : "");
                //C2_20
                temp.setDateOfQualifiRyowa(element[17] != null ? element[17].toString() : "");
                //C2_21
                temp.setQualificationDate(element[19] != null ? element[19].toString() : "");
            }
            else{
                //C1_1
                temp.setBusinessstablishmentbCode1(element[11] != null ? element[11].toString() : "");
                //C1_2
                temp.setBusinessstablishmentbCode2(element[12] != null ? element[12].toString() : "");
                //C1_3
                temp.setOfficeNumber(element[36] != null ? element[36].toString() : "");
                //C2_20
                temp.setDateOfQualifiRyowa(element[18] != null ? element[18].toString() : "");
                //C2_21
                temp.setQualificationDate(element[20] != null ? element[20].toString() : "");
            }
            this.getPersonInfor(employeeInfoList, personList, temp, element[38].toString(), ((BigDecimal)element[7]).intValue());
            temp.setHisId(element[13] != null ? element[13].toString() : "");
            //Male(1), Female(2)
            String hisId = element[13] != null ? element[13].toString() : "";
            int gender = temp.getGender();
            int undergoundDivision = Integer.valueOf(element[14].toString());
            //C2_10
            temp.setTypeMale( gender == 1 && undergoundDivision == 0 && hisId.equals("") ? 0 : 1 );
            //C2_11
            temp.setTypeFeMale( gender == 2 &&  undergoundDivision == 0 && hisId.equals("") ? 0 : 1);
            //C2_12
            temp.setTypeMiner(undergoundDivision == 1 && hisId.equals("") ? 0 : 1);
            //C2_13
            temp.setTypeMaleFund(gender == 1 && undergoundDivision == 0 && !hisId.equals("") ? 0 : 1 );
            //C2_14
            temp.setTypeFeMaleFund(gender == 2 &&  undergoundDivision == 0 && !hisId.equals("") ? 0 : 1 );
            //C2_15
            temp.setTypeMineWorkerFund(undergoundDivision == 1 && !hisId.equals("") ? 0 : 1 );

            //C2_16
            temp.setAcquiCtgHealthInsurWelfare(element[15] != null ? Integer.valueOf(element[15].toString()) : 0);
            //C2_17
            temp.setAcquiCtgMutualAidSeconded(element[15] != null ? Integer.valueOf(element[15].toString()) : 0);
            //C2_18
            temp.setAcquiCtgShipTransfer(element[15] != null ? Integer.valueOf(element[15].toString()) : 0);
            //C2_19
            temp.setPersonalNumber(element[16] != null ? element[16].toString() : "");

            //C2_22
            temp.setDependentNo(element[21] != null ? Integer.valueOf( element[21].toString()) : 0);
            //C2_23
            temp.setDependentYes(element[21] != null ? Integer.valueOf( element[21].toString()) : 0);
            //C2_24
            temp.setMonRemunerationAmountInCurrency(element[22] != null ? Integer.valueOf( element[22].toString()) : 0);
            //C2_25
            temp.setMonRemunerationAmountOfActualItem(element[23] != null ? Integer.valueOf( element[23].toString()) : 0);
            //C2_26
            temp.setCompenMonthlyAamountTotal(element[24] != null ? element[24].toString() : "");

            //C2_30
            temp.setRemarks70OldAndOverEmployees(element[25] != null  ? Integer.valueOf(element[25].toString()):0);
            //C2_31
            temp.setRemarksTwoOrMoreOfficeWorkers(element[26] != null ? Integer.valueOf(element[26].toString()) : 0);
            //C2_32
            temp.setRemarksShortTimeWorkers(element[27] != null  ? Integer.valueOf(element[27].toString()): 0);
            //C2_33
            temp.setRemarksContReemAfterRetirement(element[28] != null ? Integer.valueOf(element[28].toString()) : 0);
            //C2_34
            temp.setRemarksOther(element[29] != null  ? Integer.valueOf(element[29].toString()) : 0);
            //C2_35
            temp.setRemarksOtherContent(element[30] != null ? element[30].toString() : "");

            //C2_36
            temp.setReasonResidentAbroad(element[31] != null ? Integer.valueOf(element[31].toString()) : 0 );

            //C2_39
            temp.setReasonOtherContent(element[34] != null ? element[34].toString() : "");
            temp.setOfficeCd(element[37] != null ? element[37].toString() : "");
            temp.setUndergoundDivision(element[14] != null ? Integer.valueOf(element[14].toString()) : 1);
            data.add(temp);
        });

        return data;

    }


    private void reportTextOutputCheck(List<String> employeeIds, GeneralDate startDate, GeneralDate endDate) {
        if (startDate.after(endDate)) {
            throw new BusinessException("Msg_812");
        }
        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();

        final int DO_NOT_OUPUT = 2;
        Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSet = mSocialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(uid, cid);
        /**
         * //画面上存在していない処理 2019/8/21　河村
         */
//        if (socialInsurNotiCreateSet.get().getInsuredNumber().value == DO_NOT_OUPUT) {
//            throw new BusinessException("Msg_812",TextResource.localize("QSI001_27"));
//        }
        if (socialInsurNotiCreateSet.get().getOfficeInformation().value == DO_NOT_OUPUT) {
            throw new BusinessException("MsgQ_174", "QSI001_A222_27");
        }
        if (!socialInsurNotiCreateSet.get().getFdNumber().isPresent()) {
            throw new BusinessException("MsgQ_5", "QSI001_A222_50");
        }
        if (!socialInsurNotiCreateSet.get().getOutputFormat().isPresent()) {
            throw new BusinessException("MsgQ_5", "QSI001_46");
        }
        // check output k cần for vì đã có câu query
        boolean checkOutput = false;
        final int PEN_OFFICE = 0;
        final int HEAL_INSUR_ASS = 1;
        final int THE_WELF_PEN = 2;

        switch (socialInsurNotiCreateSet.get().getOutputFormat().get().value) {
            case PEN_OFFICE: {
                boolean emplHealInsurQualifiInfor;
                boolean empWelfarePenInsQualiInfor;

                if (/*対象区分（0：資格取得、1:資格喪失）KH cho == 0*/ 0 == 0) {
                    emplHealInsurQualifiInfor = mEmplHealInsurQualifiInforRepository.checkEmplHealInsurQualifiInforStartDate(startDate, endDate, employeeIds);
                    empWelfarePenInsQualiInfor = mEmpWelfarePenInsQualiInforRepository.checkEmpWelfarePenInsQualiInforStart(startDate, endDate, employeeIds);

                } else {
                    emplHealInsurQualifiInfor = mEmplHealInsurQualifiInforRepository.checkEmplHealInsurQualifiInforStartDate(startDate, endDate, employeeIds);
                    empWelfarePenInsQualiInfor = mEmpWelfarePenInsQualiInforRepository.checkEmpWelfarePenInsQualiInforStart(startDate, endDate, employeeIds);

                }
                if (emplHealInsurQualifiInfor && empWelfarePenInsQualiInfor) {
                    throw new BusinessException("Msg_37");
                }

                break;
            }
            case HEAL_INSUR_ASS: {
                boolean emplHealInsurQualifiInfor;
                if (/* 対象区分（0：資格取得、1:資格喪失）KH cho == 0*/ 0 == 0) {
                    emplHealInsurQualifiInfor = mEmplHealInsurQualifiInforRepository.checkEmplHealInsurQualifiInforStartDate(startDate, endDate, employeeIds);

                } else {
                    emplHealInsurQualifiInfor = mEmplHealInsurQualifiInforRepository.checkEmplHealInsurQualifiInforStartDate(startDate, endDate, employeeIds);
                }
                if (emplHealInsurQualifiInfor) {
                    throw new BusinessException("Msg_37");
                }

                break;
            }
            case THE_WELF_PEN: {
                boolean empWelfarePenInsQualiInfor;

                if (/* 対象区分（0：資格取得、1:資格喪失）KH cho == 0*/ 0 == 0) {
                    empWelfarePenInsQualiInfor = mEmpWelfarePenInsQualiInforRepository.checkEmpWelfarePenInsQualiInforStart(startDate, endDate, employeeIds);
                } else {
                    empWelfarePenInsQualiInfor = mEmpWelfarePenInsQualiInforRepository.checkEmpWelfarePenInsQualiInforStart(startDate, endDate, employeeIds);

                }
                if (empWelfarePenInsQualiInfor) {
                    throw new BusinessException("Msg_37");
                }
                break;
            }
        }

    }

}
