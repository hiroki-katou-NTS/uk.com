package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.*;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.Name;
import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.RepresentativeName;
import nts.uk.ctx.pr.shared.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInforRepository;
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
//        List<AcquiNotifiInformation> dataExport = printInsuredQualifiNoti(exportServiceContext.getQuery().employeeIds,
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

    public List<AcquiNotifiInformation> printInsuredQualifiNoti(List<String> employeeIds, SocialInsurNotiCreateSetQuery model, GeneralDate startDate, GeneralDate endDate) {
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

        /* startDate > endDate*/
        if (startDate.after(endDate)) {
            throw new BusinessException("Msg_812");
        }
        Optional<EmplHealInsurQualifiInfor> temp;
        /*design cho 対象区分（0：資格取得、1:資格喪失） == 1*/
        if (/*điều kiện chưa có */ 0 == 1) {
            /*アルゴリズム「社員健康保険資格情報が存在するかチェックする」を実行する*/
            temp = Optional.of(mEmplHealInsurQualifiInforRepository.getEmplHealInsurQualifiInfor(startDate, employeeIds));
        } else {
            /*アルゴリズム「社員厚生年金保険資格情報が存在するかチェックする」を実行する*/
            temp = Optional.of(mEmplHealInsurQualifiInforRepository.getEmplHealInsurQualifiInfor(endDate, employeeIds));
        }
        if (!temp.isPresent()) {
            throw new BusinessException("Msg_37");
        }

    }

    private List<AcquiNotifiInformation> insurQualiNotiProcess(List<String> employeeIds, GeneralDate startDate, GeneralDate endDate) {
        final int DO_NOT_OUTPUT = 3;
        final int OUTPUT_BASIC_PER_NUMBER = 1;
        final int OUTPUT_COMPANY_NAME_BusinessDivision = 0;
        final int OUTPUT_SIC_INSURES_BusinessDivision = 1;
        final int DO_NOT_OUTPUT_BusinessDivision = 2;
        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();


        List<AcquiNotifiInformation> resulfExportData = new ArrayList<>();
        AcquiNotifiInformation exDataElement = new AcquiNotifiInformation();

        Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSet = mSocialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(uid, cid);


        int valueofficeInformation = socialInsurNotiCreateSet.get().getOfficeInformation().value;
        switch (valueofficeInformation) {
            case OUTPUT_COMPANY_NAME_BusinessDivision: {
                CompanyInfor companyInfor = companyRepository.getCurrentCompany().get();
                //C1_7
                exDataElement.setBussinessName(Optional.of(new Name(companyInfor.getCompanyName())));
                //C1_5
                exDataElement.setOfficeAndressOne(Optional.of(new Address1("address information, address 1")));
                //C1_6
                exDataElement.setOfficeAndressTwo(Optional.of(new Address2("address information, address 2")));
                //C1_9
                exDataElement.setPhoneNumber(Optional.of(new PhoneNumber("address information, phone number")));
                //C1_4
                exDataElement.setOfficePostalCode(Optional.of(new PostalCode("address information, zip code")));
                //C1_8
                exDataElement.setBussinessName2(Optional.of(new RepresentativeName("Representative name")));
                break;
            }
            case OUTPUT_SIC_INSURES_BusinessDivision: {
                /* Input có コード mà k biết lấy chỗ nào */
                SocialInsuranceOffice socialInsuranceOffice = socialInsuranceOfficeRepository.findByCodeAndCid(cid, "").get();
                //C1_7
                exDataElement.setBussinessName(Optional.of(new Name(socialInsuranceOffice.getName().v())));
                //C1_5
                exDataElement.setOfficeAndressOne(Optional.of(new Address1(socialInsuranceOffice.getBasicInformation().getAddress().get().getAddress1().get().toString())));
                //C1_6
                exDataElement.setOfficeAndressTwo(Optional.of(new Address2(socialInsuranceOffice.getBasicInformation().getAddress().get().getAddress2().get().toString())));
                //C1_9
                exDataElement.setPhoneNumber(Optional.of(new PhoneNumber(socialInsuranceOffice.getBasicInformation().getAddress().get().getPhoneNumber().toString())));
                //C1_4
                exDataElement.setOfficePostalCode(Optional.of(new PostalCode(socialInsuranceOffice.getBasicInformation().getAddress().get().getPostalCode().toString())));
                //C1_8
                exDataElement.setBussinessName2(Optional.of(new RepresentativeName(socialInsuranceOffice.getBasicInformation().getRepresentativeName().toString())));
                //C1_3
                exDataElement.setOfficeNumber(Optional.of(new HealthInsuranceOfficeNumber(socialInsuranceOffice.getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1().get().toString())));
                break;
            }
            case DO_NOT_OUTPUT_BusinessDivision: {

                break;
            }
        }


        //end
        employeeIds.forEach(e -> {
            AcquiNotifiInformation exportElement = new AcquiNotifiInformation();


            Optional<EmpHealthInsurBenefits> checkEmpHealthInsurBenefits = mEmplHealInsurQualifiInforRepository.getEmplHealInsurQualifiInfor(startDate, employeeIds).getMourPeriod().stream()
                    .findFirst();

            if (checkEmpHealthInsurBenefits.isPresent()) {
                exportElement.setQualifiDate(Optional.of(checkEmpHealthInsurBenefits.get().getDatePeriod().start()));

                Optional<DateHistoryItem> mDateHistoryItem = mEmpCorpHealthOffHisRepository.getEmpCorpHealthOffHisById(employeeIds, startDate).get().getPeriod().stream().findFirst();


                Optional<AffOfficeInformation> mAffOfficeInformation;
                if (0 == 0/* điều kiện if mà chưa biết if gì =))) 対象区分（0：資格取得、1:資格喪失）*/) {
                    mAffOfficeInformation = mAffOfficeInformationRepository.getAffOfficeInformationById(e, mDateHistoryItem.get().identifier());
                } else {
                    mAffOfficeInformation = mAffOfficeInformationRepository.getAffOfficeInformationById(e, mDateHistoryItem.get().identifier());

                }
                // còn bước update mà chưa biết update như nào

                // bước này có input là so sánh pedrio với 対象区分 mà chưa rõ trường 対象区分  là gì ?
                Optional<DateHistoryItem> mDateHistoryItem2 = mEmpCorpHealthOffHisRepository.getEmpCorpHealthOffHisById(employeeIds,/*対象区分*/ startDate).get().getPeriod().stream().findFirst();
                if (0 == 0/* điều kiện if mà chưa biết if gì =))) 対象区分（0：資格取得、1:資格喪失）*/) {
                    mAffOfficeInformation = mAffOfficeInformationRepository.getAffOfficeInformationById(e, mDateHistoryItem2.get().identifier());
                } else {
                    mAffOfficeInformation = mAffOfficeInformationRepository.getAffOfficeInformationById(e, mDateHistoryItem2.get().identifier());

                }
                // update trường 保険事業所コード trong AcquiNotifiInformation nhưng k có


            }

            Optional<EmpWelfarePenInsQualiInfor> checkEmployWelPenInsurAche = Optional.of(mEmpWelfarePenInsQualiInforRepository.getEmplHealInsurQualifiInfor(startDate, employeeIds));
            if (checkEmployWelPenInsurAche.isPresent()) {
                // update  checkEmployWelPenInsurAche 取得届情報
                Optional<DateHistoryItem> mDateHistoryItem2 = mEmpCorpHealthOffHisRepository.getEmpCorpHealthOffHisById(employeeIds, startDate).get().getPeriod().stream().findFirst();

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
//                if ((exDataElement.getHealInsurAcquiDate() != null && exDataElement.getEmpPensionInsurAcquiDate() != null) && (exDataElement.getHealInsurAcquiDate() != exDataElement.getEmpPensionInsurAcquiDate()) && socialInsurAcquisiInfor.get().getPercentOrMore().get() == 1) {
//                    // update  checkEmployWelPenInsurAche 取得届情報
//                }
//                if (exDataElement.getHealInsurAcquiDate() == null && exDataElement.getEmpPensionInsurAcquiDate() != null && ((exDataElement.getHealInsurAcquiDate() != exDataElement.getEmpPensionInsurAcquiDate()) && socialInsurAcquisiInfor.get().getPercentOrMore().get() == 1)) {
//                    // update  checkEmployWelPenInsurAche 取得届情報
//                }
                {
                    // bước check nhưng chưa có EA
                }
                multiEmpWorkInfoRepository.getMultiEmpWorkInfoById(e);

                mEmployeeBasicInfoAdapter.getPersonInfo(e);

//                if (exDataElement.getHealInsurAcquiDate().get().afterOrEquals(exDataElement.getEmpPensionInsurAcquiDate().get())) {
//                    // exDataElement.setQualifiDate(exDataElement.getHealInsurAcquiDate());
//                } else {
//                    //  exDataElement.setQualifiDate(exDataElement.getEmpPensionInsurAcquiDate());
//                }
                //tính tuổi
                {

                }
                if (!exDataElement.isMoreThan70Percent()) {
                    throw new BusinessException("Msg_176");
                } else {
                    throw new BusinessException("Msg_177");
                }

            }

        });
        // check socialInsurNotiCreateSet.businessArrSymbol
        // check socialInsurNotiCreateSet.outputOrder theo 補足※21参照
        // check socialInsurNotiCreateSet.submittedName
        // check exportElement
//        if (exDataElement.getHealInsurAcquiDate() != null || exDataElement.getEmpPensionInsurAcquiDate() != null) {
//
//        }

        return resulfExportData;

    }


    // print csv
    private void printInsuredQualifiNotiAsText(List<String> employeeIds, GeneralDate startDate, GeneralDate endDate, SocialInsurNotiCreateSetQuery socialInsurNotiCreateSetQuery) {
        SocialInsurNotiCreatSetRegisProcess(socialInsurNotiCreateSetQuery);
        reportTextOutputCheck(employeeIds, startDate, endDate);
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

    private void reportTextOutputCheck(List<String> employeeIds, GeneralDate startDate, GeneralDate endDate) {
        if (startDate.after(endDate)) {
            throw new BusinessException("Msg_812");
        }
        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();

        final int DO_NOT_OUPUT = 0;
        Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSet = mSocialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(uid, cid);
        if (socialInsurNotiCreateSet.get().getInsuredNumber().value == DO_NOT_OUPUT) {
            throw new BusinessException("Msg_812", new String[]{TextResource.localize("QSI001_27")});
        }
        if (socialInsurNotiCreateSet.get().getOfficeInformation().value == DO_NOT_OUPUT) {
            throw new BusinessException("Msg_174", new String[]{TextResource.localize("QSI001_27")});
        }
        if (!socialInsurNotiCreateSet.get().getFdNumber().isPresent()) {
            throw new BusinessException("Msg_5", new String[]{TextResource.localize("QSI001_46")});
        }
        if (!socialInsurNotiCreateSet.get().getOutputFormat().isPresent()) {
            throw new BusinessException("Msg_5", new String[]{TextResource.localize("QSI001_46")});
        }
        // check output k cần for vì đã có câu query
        boolean checkOutput = false;
        final int PEN_OFFICE = 0;
        final int HEAL_INSUR_ASS = 1;
        final int THE_WELF_PEN = 2;

        switch (socialInsurNotiCreateSet.get().getOutputFormat().get().value) {
            case PEN_OFFICE: {
                EmplHealInsurQualifiInfor emplHealInsurQualifiInfor;
                EmpWelfarePenInsQualiInfor empWelfarePenInsQualiInfor;

                if (/*if nhưng chưa biết if gì 対象区分（0：資格取得、1:資格喪失）*/ 0 == 0) {
                    emplHealInsurQualifiInfor = mEmplHealInsurQualifiInforRepository.getEmplHealInsurQualifiInfor(startDate, employeeIds);
                    empWelfarePenInsQualiInfor = mEmpWelfarePenInsQualiInforRepository.getEmplHealInsurQualifiInfor(startDate, employeeIds);

                } else {
                    emplHealInsurQualifiInfor = mEmplHealInsurQualifiInforRepository.getEmplHealInsurQualifiInfor(endDate, employeeIds);
                    empWelfarePenInsQualiInfor = mEmpWelfarePenInsQualiInforRepository.getEmplHealInsurQualifiInfor(endDate, employeeIds);

                }
                if (emplHealInsurQualifiInfor == null && empWelfarePenInsQualiInfor == null) {
                    throw new BusinessException("Msg_37");
                }

                break;
            }
            case HEAL_INSUR_ASS: {
                EmplHealInsurQualifiInfor emplHealInsurQualifiInfor;
                if (/*if nhưng chưa biết if gì 対象区分（0：資格取得、1:資格喪失）*/ 0 == 0) {
                    emplHealInsurQualifiInfor = mEmplHealInsurQualifiInforRepository.getEmplHealInsurQualifiInfor(startDate, employeeIds);

                } else {
                    emplHealInsurQualifiInfor = mEmplHealInsurQualifiInforRepository.getEmplHealInsurQualifiInfor(endDate, employeeIds);
                }
                if (emplHealInsurQualifiInfor == null) {
                    throw new BusinessException("Msg_37");
                }

                break;
            }
            case THE_WELF_PEN: {
                EmpWelfarePenInsQualiInfor empWelfarePenInsQualiInfor;

                if (/*if nhưng chưa biết if gì 対象区分（0：資格取得、1:資格喪失）*/ 0 == 0) {
                    empWelfarePenInsQualiInfor = mEmpWelfarePenInsQualiInforRepository.getEmplHealInsurQualifiInfor(startDate, employeeIds);
                } else {
                    empWelfarePenInsQualiInfor = mEmpWelfarePenInsQualiInforRepository.getEmplHealInsurQualifiInfor(endDate, employeeIds);

                }
                if (empWelfarePenInsQualiInfor == null) {
                    throw new BusinessException("Msg_37");
                }
                break;
            }
        }

    }

    private void textOutputProcessing() {

    }


}
