package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformation;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformationRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.OutputFormatClass;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCrSetRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private NotificationOfLossInsExRepository notificationOfLossInsExRepository;

    @Inject
    private GuaByTheInsurExportRepository guaByTheInsurExportRepository;


    @Override
    protected void handle(ExportServiceContext<GuaByTheInsurExportQuery> exportServiceContext) {
        final int TYPE_EXPORT_EXCEL_FILE = 0;
        String userId = AppContexts.user().userId();
        String cid = AppContexts.user().companyId();
        List<PensionOfficeDataExport> pension = new ArrayList<>();
        List<PensionOfficeDataExport> healthInsAss = new ArrayList<>();
        List<EmpPenFundSubData> empPensionFund = new ArrayList<>();
        CompanyInfor company = notificationOfLossInsExRepository.getCompanyInfor(AppContexts.user().companyId());
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
                exportServiceContext.getQuery().getSocialInsurNotiCreateSetQuery().getLineFeedCode(),
                exportServiceContext.getQuery().getSocialInsurNotiCreateSetQuery().getOutputFormat()
        );
        mSocialInsurNotiCrSetRepository.update(ins);
        if (exportServiceContext.getQuery().getTypeExport() != TYPE_EXPORT_EXCEL_FILE) {
            if(ins.getOutputFormat().get() == OutputFormatClass.PEN_OFFICE) {
                pension = guaByTheInsurExportRepository.getDataExportCSV(exportServiceContext.getQuery().getEmpIds(), cid,
                        exportServiceContext.getQuery().getStartDate(), exportServiceContext.getQuery().getEndDate());
            }
            if(ins.getOutputFormat().get() == OutputFormatClass.HEAL_INSUR_ASSO) {
                healthInsAss = guaByTheInsurExportRepository.getDataHealthInsAss(exportServiceContext.getQuery().getEmpIds(), cid,
                        exportServiceContext.getQuery().getStartDate(), exportServiceContext.getQuery().getEndDate());
            }
            if(ins.getOutputFormat().get() == OutputFormatClass.THE_WELF_PEN) {
                empPensionFund = guaByTheInsurExportRepository.getDataEmpPensionFund(exportServiceContext.getQuery().getEmpIds(), cid,
                        exportServiceContext.getQuery().getStartDate(), exportServiceContext.getQuery().getEndDate());
            }
        }
        ExportDataCsv exportData = ExportDataCsv.builder()
                .listContent(printInsuredQualifiNoti(exportServiceContext.getQuery().getEmpIds(),
                        exportServiceContext.getQuery().getStartDate(), exportServiceContext.getQuery().getEndDate())
                )
                .ins(ins)
                .infor(infor)
                .company(company)
                .empPenFundSub(empPensionFund)
                .pensionOfficeData(pension.isEmpty() ? healthInsAss : pension)
                .build();

        if (exportServiceContext.getQuery().getTypeExport() == TYPE_EXPORT_EXCEL_FILE) {
            checkAcquiNotiInsurProcess(exportServiceContext.getQuery().getEmpIds(), exportServiceContext.getQuery().getStartDate(), exportServiceContext.getQuery().getStartDate())
            generatorExcel.generate(exportServiceContext.getGeneratorContext(), exportData);
            return;
        }
        generatorCsv.generate(exportServiceContext.getGeneratorContext(), exportData);
    }

    private List<GuaByTheInsurExportDto> printInsuredQualifiNoti(List<String> employeeIds, GeneralDate startDate, GeneralDate endDate) {
        return insurQualiNotiProcess(employeeIds, startDate, endDate);
    }

    private void checkAcquiNotiInsurProcess(List<String> employeeIds, GeneralDate startDate, GeneralDate endDate) {
        if (startDate.after(endDate)) {
            throw new BusinessException("Msg_812");
        }
        boolean checkHealInsur = mEmplHealInsurQualifiInforRepository.checkEmplHealInsurQualifiInforEndDate(startDate, endDate, employeeIds);
        boolean checkWelfarePen = mEmpWelfarePenInsQualiInforRepository.checkEmpWelfarePenInsQualiInforStart(startDate, endDate, employeeIds);
        if (checkHealInsur && checkWelfarePen) {
            throw new BusinessException("Msg_37");
        }

    }

    private List<GuaByTheInsurExportDto> insurQualiNotiProcess(List<String> employeeIds, GeneralDate startDate, GeneralDate endDate) {
        final int Enum_SubNameClass_PERSONAL_NAME = 0;
        final int Enum_BusinessDivision_OUTPUT_COMPANY_NAME = 0;
        final int Enum_BussEsimateClass_HEAL_INSUR_OFF_ARR_SYMBOL = 0;
        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();

        List<GuaByTheInsurExportDto> data = new ArrayList<>();
        List<Object[]> dataExport = repo.getDataExport(employeeIds,cid,uid,startDate,endDate);
        dataExport.forEach(element -> {
            GuaByTheInsurExportDto temp = new GuaByTheInsurExportDto();

            //C1_10
            temp.setFilingDate(startDate.toString());

            if(Integer.valueOf(element[0].toString()) == Enum_BusinessDivision_OUTPUT_COMPANY_NAME){
                    //C1_4
                    temp.setOfficePostalCode(element[9] != null ? element[9].toString() : "");
                    //C1_5
                    temp.setOfficeAddress1(element[3] != null ? element[3].toString() : "");
                    //C1_6
                    temp.setOfficeAddress2(element[5] != null ? element[5].toString() : "");
                    //C1_7
                    temp.setBusinessName(element[1] != null ? element[1].toString() : "");
                    //C1_8
                    temp.setBusinessName1(element[11] != null ? element[12].toString() : "");
                    //C1_9
                    temp.setPhoneNumber(element[7] != null ? element[7].toString() : "");
                    //C2_28
                    temp.setStreetAddress(element[3] != null ? element[3].toString() : "");
                    //C2_29
                    temp.setAddressKana(element[5] != null ? element[5].toString() : "");
            }
            else{
                //set data to file output
                {
                    //C1_4
                    temp.setOfficePostalCode(element[10] != null ? element[10].toString() : "");
                    //C1_5
                    temp.setOfficeAddress1(element[4] != null ? element[4].toString() : "");
                    //C1_6
                    temp.setOfficeAddress2(element[6] != null ? element[6].toString() : "");
                    //C1_7
                    temp.setBusinessName(element[2] != null ? element[2].toString() : "");
                    //C1_8
                    temp.setBusinessName1(element[12] != null ? element[12].toString() : "");
                    //C1_9
                    temp.setPhoneNumber(element[8] != null ? element[8].toString() : "");
                    //C2_28
                    temp.setStreetAddress(element[4] != null ? element[4].toString() : "");
                    //C2_29
                    temp.setAddressKana(element[6] != null ? element[6].toString() : "");
                }
            }
            if(Integer.valueOf(element[14].toString()) == Enum_BussEsimateClass_HEAL_INSUR_OFF_ARR_SYMBOL){
                //C1_1
                temp.setBusinessstablishmentbCode1(element[15] != null ? element[15].toString() : "");
                //C1_2
                temp.setBusinessstablishmentbCode2(element[16] != null ? element[16].toString() : "");
                //C1_3
                temp.setOfficeNumber(element[52] != null ? element[52].toString() : "");
                //C2_20
                temp.setDateOfQualifiRyowa(element[33] != null ? element[33].toString() : "");
                //C2_21
                temp.setQualificationDate(element[33] != null ? element[33].toString() : "");
            }
            else{
                //C1_1
                temp.setBusinessstablishmentbCode1(element[17] != null ? element[17].toString() : "");
                //C1_2
                temp.setBusinessstablishmentbCode2(element[18] != null ? element[18].toString() : "");
                //C1_3
                temp.setOfficeNumber(element[53] != null ? element[53].toString() : "");
                //C2_20
                temp.setDateOfQualifiRyowa(element[34] != null ? element[34].toString() : "");
                //C2_21
                temp.setQualificationDate(element[34] != null ? element[34].toString() : "");
            }
            if(Integer.valueOf(element[14].toString()) == Enum_SubNameClass_PERSONAL_NAME){
                //C2_2
                temp.setNameOfInsuredPersonMr(element[19] != null ? element[19].toString() : "");
                //C2_3
                temp.setNameOfInsuredPerson(element[19] != null ? element[19].toString() : "");
                //C2_4
                temp.setNameOfInsuredPersonMrK(element[23] != null ? element[23].toString() : "");
                //C2_5
                temp.setNameOfInsuredPerson1(element[24] != null ? element[24].toString() : "");

            }
            else{
                //C2_2
                temp.setNameOfInsuredPersonMr(element[20] != null ? element[20].toString() : "");
                //C2_3
                temp.setNameOfInsuredPerson(element[20] != null ? element[20].toString() : "");
                //C2_4
                temp.setNameOfInsuredPersonMrK(element[24] != null ? element[24].toString() : "");
                //C2_5
                temp.setNameOfInsuredPerson1(element[24] != null ? element[24].toString() : "");
            }
            //C1_10
            //C2_6 => covert to Date of birth (Showa)
            temp.setBrithDayShowa(element[27] != null ? element[27].toString() : "");
            //C2_7 => covert to Date of birth (Heisei)
            temp.setBrithDayHeisei(element[27] != null ? element[27].toString() : "");
            //C2_8
            temp.setBrithDayRyowa(element[27] != null ? element[27].toString() : "");
            //C2_9
            temp.setBrithDay(element[27] != null ? element[27].toString() : "");
            //C2_10
            temp.setTypeMale(Integer.valueOf(element[29].toString()) == 1 ? 1 : 0 );
            //C2_11
            temp.setTypeFeMale(Integer.valueOf(element[29].toString()) == 2 ? 2 : 0 );
            //C2_12
            temp.setTypeMiner(element[31] != null ? element[31].toString() : "");
            //C2_13
            temp.setTypeMaleFund(element[31] != null ? element[31].toString() : "");
            //C2_14
            temp.setTypeFeMaleFund(element[31] != null ? element[31].toString() : "");
            //C2_15
            temp.setTypeMineWorkerFund(element[31] != null ? element[31].toString() : "");
            //C2_16
            temp.setAcquiCtgHealthInsurWelfare(element[32] != null ? Integer.valueOf(element[32].toString()) : 0);
            //C2_17
            temp.setAcquiCtgMutualAidSeconded(element[32] != null ? Integer.valueOf(element[32].toString()) : 0);
            //C2_18
            temp.setAcquiCtgShipTransfer(element[32] != null ? Integer.valueOf(element[32].toString()) : 0);
            //C2_19
            temp.setPersonalNumber(element[33] != null ? element[33].toString() : "");

            //C2_22
            temp.setDependentNo(element[38] != null ? Integer.valueOf( element[38].toString()) : 0);
            //C2_23
            temp.setDependentYes(element[38] != null ? Integer.valueOf( element[38].toString()) : 0);
            //C2_24
            temp.setMonRemunerationAmountInCurrency(element[39] != null ? element[39].toString() : "");
            //C2_25
            temp.setMonRemunerationAmountOfActualItem(element[40] != null ? element[40].toString() : "");
            //C2_26
            temp.setCompenMonthlyAamountTotal(element[41] != null ? element[41].toString() : "");
            //C2_27
            temp.setPostalCode(element[9] != null ? element[9].toString() : "");

            //C2_30
            temp.setRemarks70OldAndOverEmployees(element[42] != null  ? Integer.valueOf(element[42].toString()):0);
            //C2_31
            temp.setRemarksTwoOrMoreOfficeWorkers(element[43] != null ? Integer.valueOf(element[43].toString()) : 0);
            //C2_32
            temp.setRemarksShortTimeWorkers(element[44] != null  ? Integer.valueOf(element[44].toString()): 0);
            //C2_33
            temp.setRemarksContReemAfterRetirement(element[45] != null ? Integer.valueOf(element[45].toString()) : 0);
            //C2_34
            temp.setRemarksOther(element[46] != null  ? Integer.valueOf(element[46].toString()) : 0);
            //C2_35
            temp.setRemarksOtherContent(element[47] != null ? element[47].toString() : "");
            //C2_36
            temp.setReasonResidentAbroad(element[48] != null ? Integer.valueOf(element[48].toString()) : 1);
            //C2_37
            temp.setReasonShortTermResidence(element[49] != null ? Integer.valueOf(element[49].toString()) : 1);
            //C2_38
            temp.setReasonOther(element[50] != null ? Integer.valueOf(element[50].toString()) : 1);
            //C2_39
            temp.setReasonOtherContent(element[51] != null ? element[51].toString() : "");
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

        final int DO_NOT_OUPUT = 0;
        Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSet = mSocialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(uid, cid);
        if (socialInsurNotiCreateSet.get().getInsuredNumber().value == DO_NOT_OUPUT) {
            throw new BusinessException("Msg_812",TextResource.localize("QSI001_27"));
        }
        if (socialInsurNotiCreateSet.get().getOfficeInformation().value == DO_NOT_OUPUT) {
            throw new BusinessException("Msg_174", TextResource.localize("QSI001_27"));
        }
        if (!socialInsurNotiCreateSet.get().getFdNumber().isPresent()) {
            throw new BusinessException("Msg_5", TextResource.localize("QSI001_46"));
        }
        if (!socialInsurNotiCreateSet.get().getOutputFormat().isPresent()) {
            throw new BusinessException("Msg_5", TextResource.localize("QSI001_46"));
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
                    emplHealInsurQualifiInfor = mEmplHealInsurQualifiInforRepository.checkEmplHealInsurQualifiInforEndDate(startDate, endDate, employeeIds);
                    empWelfarePenInsQualiInfor = mEmpWelfarePenInsQualiInforRepository.checkEmpWelfarePenInsQualiInforEnd(startDate, endDate, employeeIds);

                } else {
                    emplHealInsurQualifiInfor = mEmplHealInsurQualifiInforRepository.checkEmplHealInsurQualifiInforEndDate(startDate, endDate, employeeIds);
                    empWelfarePenInsQualiInfor = mEmpWelfarePenInsQualiInforRepository.checkEmpWelfarePenInsQualiInforEnd(startDate, endDate, employeeIds);

                }
                if (emplHealInsurQualifiInfor && empWelfarePenInsQualiInfor) {
                    throw new BusinessException("Msg_37");
                }

                break;
            }
            case HEAL_INSUR_ASS: {
                boolean emplHealInsurQualifiInfor;
                if (/* 対象区分（0：資格取得、1:資格喪失）KH cho == 0*/ 0 == 0) {
                    emplHealInsurQualifiInfor = mEmplHealInsurQualifiInforRepository.checkEmplHealInsurQualifiInforEndDate(startDate, endDate, employeeIds);

                } else {
                    emplHealInsurQualifiInfor = mEmplHealInsurQualifiInforRepository.checkEmplHealInsurQualifiInforEndDate(startDate, endDate, employeeIds);
                }
                if (emplHealInsurQualifiInfor) {
                    throw new BusinessException("Msg_37");
                }

                break;
            }
            case THE_WELF_PEN: {
                boolean empWelfarePenInsQualiInfor;

                if (/* 対象区分（0：資格取得、1:資格喪失）KH cho == 0*/ 0 == 0) {
                    empWelfarePenInsQualiInfor = mEmpWelfarePenInsQualiInforRepository.checkEmpWelfarePenInsQualiInforEnd(startDate, endDate, employeeIds);
                } else {
                    empWelfarePenInsQualiInfor = mEmpWelfarePenInsQualiInforRepository.checkEmpWelfarePenInsQualiInforEnd(startDate, endDate, employeeIds);

                }
                if (empWelfarePenInsQualiInfor) {
                    throw new BusinessException("Msg_37");
                }
                break;
            }
        }

    }

}
