package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.*;
import nts.uk.ctx.pr.shared.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmployWelPenInsurAche;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfarePenTypeInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformationRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
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
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    @Inject
    private EmpWelfarePenInsQualiInforRepository mEmpWelfarePenInsQualiInforRepository;

    @Inject
    private SocialInsurNotiCrSetRepository mSocialInsurNotiCrSetRepository;

    @Inject
    private EmplHealInsurQualifiInforRepository mEmplHealInsurQualifiInforRepository;

    @Inject
    private EmpCorpHealthOffHisRepository mEmpCorpHealthOffHisRepository;

    @Inject
    private AffOfficeInformationRepository mAffOfficeInformationRepository;

    @Inject
    private WelfarePenTypeInforRepository mWelfarePenTypeInforRepository;

    @Inject
    private EmPensionFundPartiPeriodInforRepository mEmPensionFundPartiPeriodInforRepository;

    @Inject
    private SocialInsurAcquisiInforRepository mSocialInsurAcquisiInforRepository;

    @Inject
    private MultiEmpWorkInfoRepository multiEmpWorkInfoRepository;

    @Inject
    private PersonInfoAdapter mEmployeeBasicInfoAdapter;


    @Inject
    private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

    /**
     * The company repository.
     */
    @Inject
    private CompanyAdapter companyRepository;


    @Override
    protected void handle(ExportServiceContext<GuaByTheInsurExportQuery> exportServiceContext) {
        final int TYPE_EXPORT_EXCEL_FILE = 0;
//        List<GuaByTheInsurExportDto> dataExport = printInsuredQualifiNoti(exportServiceContext.getQuery().employeeIds,
//                exportServiceContext.getQuery().socialInsurNotiCreateSetQuery,
//                exportServiceContext.getQuery().startDate,
//                exportServiceContext.getQuery().endDate
//        );
        List<GuaByTheInsurExportDto> dataExport = new ArrayList<>();

        ExportDataCsv exportData = ExportDataCsv.builder()
                .lstHeader(this.finHeader())
                .listContent(dataExport)
                .build();

        if (exportServiceContext.getQuery().typeExport == TYPE_EXPORT_EXCEL_FILE) {

            generatorExcel.generate(exportServiceContext.getGeneratorContext(), exportData);
            return;
        }
        generatorCsv.generate(exportServiceContext.getGeneratorContext(), exportData);


    }

    /**
     * Fin header.
     *
     * @return the list
     */
    private List<String> finHeader() {
        List<String> lstHeader = new ArrayList<>();
        /** The Constant LST_NAME_ID. */
        final List<String> LST_NAME_ID_HEADER = Arrays.asList("KSU006_210", "KSU006_211", "KSU006_207",
                "KSU006_208", "KSU006_209", "KSU006_212");
        for (String nameId : LST_NAME_ID_HEADER) {
            lstHeader.add(TextResource.localize(nameId));
        }
        return lstHeader;
    }

    public List<GuaByTheInsurExportDto> printInsuredQualifiNoti(List<String> employeeIds, SocialInsurNotiCreateSetQuery model, GeneralDate startDate, GeneralDate endDate) {
        settingRegisProcess(model);
        checkAcquiNotiInsurProcess(employeeIds, 1, startDate, endDate);
//        return insurQualiNotiProcess(employeeIds, startDate, endDate);
        return new ArrayList<>();
    }


    private void settingRegisProcess(SocialInsurNotiCreateSetQuery model) {
        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();
        Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSet = mSocialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(uid, cid);
        SocialInsurNotiCreateSet input = new SocialInsurNotiCreateSet(
                cid,
                uid,
                model.getOfficeInformation(),
                model.getBusinessArrSymbol(),
                model.getOutputOrder(),
                model.getPrintPersonNumber(),
                model.getSubmittedName(),
                model.getInsuredNumber(),
                model.getFdNumber(),
                model.getTextPersonNumber(),
                model.getOutputFormat(),
                model.getLineFeedCode()

        );
        if (!socialInsurNotiCreateSet.isPresent()) {
            mSocialInsurNotiCrSetRepository.add(input);
            return;
        }
        mSocialInsurNotiCrSetRepository.update(input);
        return;
    }

    private void checkAcquiNotiInsurProcess(List<String> employeeIds, int targetAtr, GeneralDate startDate, GeneralDate endDate) {


        List<EmplHealInsurQualifiInfor> empExistHeal = new ArrayList<>();
        /* startDate > endDate*/
        if (startDate.after(endDate)) {
            throw new BusinessException("Msg_812");
        }
        /*アルゴリズム「社員健康保険資格情報」を取得する*/
//        List<EmplHealInsurQualifiInfor> emplHealInsurQualifiInfors = emplHealInsurQualifiInforRepository.getAllEmplHealInsurQualifiInfor();
//        EmplHealInsurQualifiInfor temp;
//        for (int i = 0; i < employeeIds.size(); i++) {
//            int finalI = i;
//            temp = emplHealInsurQualifiInfors.stream().filter(e -> e.getEmployeeId().equals(employeeIds.get(finalI))).findFirst().get();
//            boolean checkHeal = temp.getMourPeriod().stream().filter(e -> startDate.beforeOrEquals(e.getDatePeriod().start()) && endDate.afterOrEquals(e.getDatePeriod().end())).findFirst().isPresent();
//            if (checkHeal) {
//                empExistHeal.add(temp);
//            }
//        }
//
//        //アルゴリズム「社員厚生年金保険資格情報」を取得する
//        List<EmpWelfarePenInsQualiInfor> empExistWelfare = new ArrayList<>();
//        List<EmpWelfarePenInsQualiInfor> empWelfarePenInsQualiInfors = mEmpWelfarePenInsQualiInforRepository.getAllEmpWelfarePenInsQualiInfor();
//        EmpWelfarePenInsQualiInfor tempEmpWelfare;
//        for (int i = 0; i < employeeIds.size(); i++) {
//            int finalI = i;
//            tempEmpWelfare = empWelfarePenInsQualiInfors.stream().filter(e -> e.getEmployeeId().equals(employeeIds.get(finalI))).findFirst().get();
//            boolean checkHeal = tempEmpWelfare.getMournPeriod().stream().filter(e -> startDate.beforeOrEquals(e.getDatePeriod().start()) && endDate.afterOrEquals(e.getDatePeriod().end())).findFirst().isPresent();
//            if (checkHeal) {
//                empExistWelfare.add(tempEmpWelfare);
//            }
//        }
        List<EmplHealInsurQualifiInfor> emplHealInsurQualifiInfors = emplHealInsurQualifiInforRepository.getAllEmplHealInsurQualifiInfor();
        List<EmpWelfarePenInsQualiInfor> empWelfarePenInsQualiInfors = mEmpWelfarePenInsQualiInforRepository.getAllEmpWelfarePenInsQualiInfor();

        employeeIds.stream().forEach(q -> {
            if (/*điều kiện chưa có */ 0 == 0) {
                {/*アルゴリズム「社員健康保険資格情報が存在するかチェックする」を実行する*/
                    Optional<EmplHealInsurQualifiInfor> temp = emplHealInsurQualifiInfors.stream().filter(e -> e.getEmployeeId().equals(q)).findFirst();
                    if (temp.isPresent()) {
                        temp.get().getMourPeriod().stream()
                                .filter(
                                        e -> startDate.beforeOrEquals(e.getDatePeriod().start()) && endDate.afterOrEquals(e.getDatePeriod().end())
                                ).findFirst()
                                .isPresent();
                    }
                }
                {/*アルゴリズム「社員厚生年金保険資格情報が存在するかチェックする」を実行する*/
                    emplHealInsurQualifiInfors.stream().filter(e -> e.getEmployeeId().equals(q)).findFirst();
                }
            }
        });


    }

    private List<GuaByTheInsurExportDto> insurQualiNotiProcess(List<String> employeeIds, GeneralDate startDate, GeneralDate endDate) {
        final int DO_NOT_OUTPUT = 3;
        final int OUTPUT_BASIC_PER_NUMBER = 1;
        final int OUTPUT_COMPANY_NAME_BusinessDivision = 0;
        final int OUTPUT_SIC_INSURES_BusinessDivision = 1;
        final int DO_NOT_OUTPUT_BusinessDivision = 2;
        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();
        List<GuaByTheInsurExportDto> resulfExportData = new ArrayList<>();

        employeeIds.forEach(e -> {
            GuaByTheInsurExportDto exDataElement = new GuaByTheInsurExportDto();

            Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSet = mSocialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(uid, cid);
            // C2_2,3,4,5
            exDataElement.setSubmittedName(socialInsurNotiCreateSet.get().getSubmittedName().value);


            int valuePrintPersonNumber = socialInsurNotiCreateSet.get().getPrintPersonNumber().value;
            int valueofficeInformation = socialInsurNotiCreateSet.get().getOfficeInformation().value;

            if (valuePrintPersonNumber == DO_NOT_OUTPUT || valuePrintPersonNumber == OUTPUT_BASIC_PER_NUMBER) {
                switch (valueofficeInformation) {
                    case OUTPUT_COMPANY_NAME_BusinessDivision: {
                        CompanyInfor companyInfor = companyRepository.getCurrentCompany().get();
                        //C1_7
                        exDataElement.setBussinessName(companyInfor.getCompanyName());
                        //C1_5
                        exDataElement.setOfficeAndressOne("address information, address 1");
                        //C1_6
                        exDataElement.setOfficeAndressTwo("address information, address 2");
                        //C1_9
                        exDataElement.setPhoneNumber("address information, phone number");
                        //C1_4
                        exDataElement.setOfficePostalCode("address information, zip code");
                        //C1_8
                        exDataElement.setBussinessName2("Representative name");
                        break;
                    }
                    case OUTPUT_SIC_INSURES_BusinessDivision: {
                        /* Input có コード mà k biết lấy chỗ nào */
                        SocialInsuranceOffice socialInsuranceOffice = socialInsuranceOfficeRepository.findByCodeAndCid(cid, "").get();
                        //C1_7
                        exDataElement.setBussinessName(socialInsuranceOffice.getName().v());
                        //C1_5
                        exDataElement.setOfficeAndressOne(socialInsuranceOffice.getBasicInformation().getAddress().get().getAddress1().get().toString());
                        //C1_6
                        exDataElement.setOfficeAndressTwo(socialInsuranceOffice.getBasicInformation().getAddress().get().getAddress2().get().toString());
                        //C1_9
                        exDataElement.setPhoneNumber(socialInsuranceOffice.getBasicInformation().getAddress().get().getPhoneNumber().toString());
                        //C1_4
                        exDataElement.setOfficePostalCode(socialInsuranceOffice.getBasicInformation().getAddress().get().getPostalCode().toString());
                        //C1_8
                        exDataElement.setBussinessName2(socialInsuranceOffice.getBasicInformation().getRepresentativeName().toString());
                        //C1_3
                        // cần confirm chính xác trường cần fill vì có 4 enum trong trường này
                        exDataElement.setOfficeNumber(socialInsuranceOffice.getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1().get().toString());
                        break;
                    }
                    case DO_NOT_OUTPUT_BusinessDivision: {

                        break;
                    }
                }
                //end

            Optional<EmpHealthInsurBenefits> checkEmpHealthInsurBenefits = mEmplHealInsurQualifiInforRepository.getAllEmplHealInsurQualifiInfor().stream()
                    .filter(a -> a.getEmployeeId().equals(e))
                    .findFirst().get().getMourPeriod().stream()
                    .filter(b -> startDate.beforeOrEquals(b.getDatePeriod().start()) && endDate.afterOrEquals(b.getDatePeriod().end()))
                    .findFirst();

            if (checkEmpHealthInsurBenefits.isPresent()) {

                //update checkEmpHealthInsurBenefits ( ワークモデル「取得届情報」を更新する )
                exDataElement.setQualifiDate(null/*Optional.ofNullable(checkEmpHealthInsurBenefits.get().getDatePeriod())*/);
                //
                Optional<DateHistoryItem> mDateHistoryItem = mEmpCorpHealthOffHisRepository.getAllEmpCorpHealthOffHis().stream()
                        .filter(d -> d.getEmployeeId().equals(e)).findFirst()
                        .get()
                        .getPeriod()
                        .stream()
                        .filter(f -> startDate.beforeOrEquals(f.start()) && endDate.afterOrEquals(f.end()))
                        .findFirst();
                if(0==0/* điều kiện if mà chưa biết if gì =))) 対象区分（0：資格取得、1:資格喪失）*/){
                    Optional<AffOfficeInformation> mAffOfficeInformation = mAffOfficeInformationRepository.getAllAffOfficeInformation().stream()
                            .filter(i -> i.getHistoryId().equals(mDateHistoryItem.get().identifier()))
                            .findFirst();
                }
                else{
                    Optional<AffOfficeInformation> mAffOfficeInformation = mAffOfficeInformationRepository.getAllAffOfficeInformation().stream()
                            .filter(i -> i.getHistoryId().equals(mDateHistoryItem.get().identifier()))
                            .findFirst();
                }

                Optional<AffOfficeInformation> mAffOfficeInformation = mAffOfficeInformationRepository.getAllAffOfficeInformation().stream()
                        .filter(i -> i.getHistoryId().equals(mDateHistoryItem.get().identifier()))
                        .findFirst();


            }

            Optional<EmployWelPenInsurAche> checkEmployWelPenInsurAche = mEmpWelfarePenInsQualiInforRepository.getAllEmpWelfarePenInsQualiInfor().stream()
                    .filter(o -> o.getEmployeeId().equals(e))
                    .findFirst().get().getMournPeriod().stream()
                    .filter(a -> startDate.beforeOrEquals(a.getDatePeriod().start()) && endDate.afterOrEquals(a.getDatePeriod().end()))
                    .findFirst();
            if (checkEmployWelPenInsurAche.isPresent()) {

                // update  checkEmployWelPenInsurAche 取得届情報

                Optional<DateHistoryItem> mDateHistoryItem2 = mEmpCorpHealthOffHisRepository.getAllEmpCorpHealthOffHis().stream().filter(s -> s.getEmployeeId().equals(e))
                        .findFirst()
                        .get()
                        .getPeriod()
                        .stream()
                        .filter(f -> startDate.beforeOrEquals(f.start()) && endDate.afterOrEquals(f.end()))
                        .findFirst();

                //
                Optional<AffOfficeInformation> mAffOfficeInformationTemp2 = mAffOfficeInformationRepository.getAllAffOfficeInformation().stream()
                        .filter(i -> i.getHistoryId().equals(mDateHistoryItem2.get().identifier()))
                        .findFirst();

                //
                mWelfarePenTypeInforRepository.getWelfarePenTypeInforById(mAffOfficeInformationTemp2.get().getHistoryId());

                mEmPensionFundPartiPeriodInforRepository.getAllEmPensionFundPartiPeriodInfor().stream()
                        .filter(g -> g.getEmployeeId().equals(e) && startDate.beforeOrEquals(g.getDatePeriod().start()) && endDate.afterOrEquals(g.getDatePeriod().end()))
                        .findFirst();

                Optional<SocialInsurAcquisiInfor> socialInsurAcquisiInfor = mSocialInsurAcquisiInforRepository.getSocialInsurAcquisiInforById(e);


                //check
                if ((exDataElement.getHealInsurAcquiDate() != null && exDataElement.getEmpPensionInsurAcquiDate() != null) && (exDataElement.getHealInsurAcquiDate() != exDataElement.getEmpPensionInsurAcquiDate()) && socialInsurAcquisiInfor.get().getPercentOrMore().get() == 1) {
                    // update  checkEmployWelPenInsurAche 取得届情報
                }
                if (exDataElement.getHealInsurAcquiDate() == null && exDataElement.getEmpPensionInsurAcquiDate() != null && ((exDataElement.getHealInsurAcquiDate() != exDataElement.getEmpPensionInsurAcquiDate()) && socialInsurAcquisiInfor.get().getPercentOrMore().get() == 1)) {
                    // update  checkEmployWelPenInsurAche 取得届情報
                }
                {
                    // bước check nhưng chưa có EA
                }
                multiEmpWorkInfoRepository.getMultiEmpWorkInfoById(e);

                mEmployeeBasicInfoAdapter.getPersonInfo(e);

                if (exDataElement.getHealInsurAcquiDate().get().afterOrEquals(exDataElement.getEmpPensionInsurAcquiDate().get())) {
                   // exDataElement.setQualifiDate(exDataElement.getHealInsurAcquiDate());
                } else {
                  //  exDataElement.setQualifiDate(exDataElement.getEmpPensionInsurAcquiDate());
                }
                //tính tuổi
                {

                }
                if (!exDataElement.isMoreThan70Percent()) {
                    throw new BusinessException("Msg_176");
                } else {
                    throw new BusinessException("Msg_177");
                }

            }

        }









            if (exDataElement.getHealInsurAcquiDate() != null || exDataElement.getEmpPensionInsurAcquiDate() != null) {

            }
        });
        return resulfExportData;

    }


    // print csv
    private void printInsuredQualifiNotiAsText(SocialInsurNotiCreateSetQuery socialInsurNotiCreateSetQuery) {
        SocialInsurNotiCreatSetRegisProcess(socialInsurNotiCreateSetQuery);
        reportTextOutputCheck(null, null);
    }

    private void SocialInsurNotiCreatSetRegisProcess(SocialInsurNotiCreateSetQuery socialInsurNotiCreateSetQuery) {
        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();
        final int DO_NOT_OUPUT = 0;
        Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSet = mSocialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(uid, cid);
        if (!socialInsurNotiCreateSet.isPresent()) {
            socialInsurNotiCreateSet = Optional.ofNullable(new SocialInsurNotiCreateSet(cid,
                    uid,
                    EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getOfficeInformation(), BusinessDivision.class).value,
                    EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getBusinessArrSymbol(), BussEsimateClass.class).value,
                    EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getOutputOrder(), SocialInsurOutOrder.class).value,
                    EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getPrintPersonNumber(), PersonalNumClass.class).value,
                    EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getSubmittedName(), SubNameClass.class).value,
                    EnumAdaptor.valueOf(DO_NOT_OUPUT, InsurPersonNumDivision.class).value,
                    Optional.ofNullable(socialInsurNotiCreateSetQuery.getFdNumber()).get(),
                    Optional.ofNullable(EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getTextPersonNumber(), TextPerNumberClass.class)).get().value,
                    Optional.ofNullable(EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getOutputFormat(), OutputFormatClass.class)).get().value,
                    Optional.ofNullable(EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getLineFeedCode(), LineFeedCode.class)).get().value
            ));
        } else {
            socialInsurNotiCreateSet.get().setSubmittedName(EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getSubmittedName(), SubNameClass.class));
            socialInsurNotiCreateSet.get().setOutputOrder(EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getOutputOrder(), SocialInsurOutOrder.class));
            socialInsurNotiCreateSet.get().setInsuredNumber(EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getInsuredNumber(), InsurPersonNumDivision.class));
            socialInsurNotiCreateSet.get().setOfficeInformation(EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getOfficeInformation(), BusinessDivision.class));
            socialInsurNotiCreateSet.get().setPrintPersonNumber(EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getPrintPersonNumber(), PersonalNumClass.class));
            socialInsurNotiCreateSet.get().setOutputFormat(Optional.ofNullable(EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getOutputFormat(), OutputFormatClass.class)));
            socialInsurNotiCreateSet.get().setTextPersonNumber(Optional.ofNullable(EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getTextPersonNumber(), TextPerNumberClass.class)));
            socialInsurNotiCreateSet.get().setLineFeedCode(Optional.ofNullable(EnumAdaptor.valueOf(socialInsurNotiCreateSetQuery.getLineFeedCode(), LineFeedCode.class)));
        }
    }

    private void reportTextOutputCheck(GeneralDate startDate, GeneralDate endDate) {

    }

}
