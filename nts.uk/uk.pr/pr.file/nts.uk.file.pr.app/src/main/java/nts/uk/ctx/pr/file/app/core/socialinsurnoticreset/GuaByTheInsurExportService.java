package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.InsurPersonNumDivision;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.SocialInsurNotiCrSetRepository;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmployWelPenInsurAche;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfarePenTypeInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformationRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.query.pub.person.EmployeeInfoExport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class GuaByTheInsurExportService extends ExportService {

    @Inject
    private GuaByTheInsurExportGenerator generator;

    @Inject
    private GuaByTheInsurExportRepository repo;

    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    @Inject
    private EmpWelfarePenInsQualiInforRepository mEmpWelfarePenInsQualiInforRepository;

    @Inject
    private SocialInsurNotiCrSetRepository mSocialInsurNotiCrSetRepository;

    @Inject
    private EmployeeInfoExport mEmployeeInfoExport;

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


    @Override
    protected void handle(ExportServiceContext exportServiceContext) {

        generator.generate(exportServiceContext.getGeneratorContext(), new ArrayList<>());

    }

    public void printInsuredQualifiNoti(List<String> employeeIds, GuaByTheInsurDto model, GeneralDate startDate, GeneralDate endDate) {
        settingRegisProcess(model);
        checkAcquiNotiInsurProcess(employeeIds, startDate, endDate);
        insurQualiNotiProcess(employeeIds, startDate, endDate);
    }

    private void settingRegisProcess(GuaByTheInsurDto model) {

        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();
        Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSet = mSocialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(uid, cid);
        SocialInsurNotiCreateSet input = new SocialInsurNotiCreateSet(
                cid,
                uid,
                model.getSocialInsurNotiCreateSet().get().getOfficeInformation(),
                model.getSocialInsurNotiCreateSet().get().getBusinessArrSymbol(),
                model.getSocialInsurNotiCreateSet().get().getOutputOrder(),
                model.getSocialInsurNotiCreateSet().get().getPrintPersonNumber(),
                model.getSocialInsurNotiCreateSet().get().getSubmittedName(),
                InsurPersonNumDivision.DO_NOT_OUPUT,
                model.getSocialInsurNotiCreateSet().get().getFdNumber(),
                model.getSocialInsurNotiCreateSet().get().getTextPersonNumber(),
                model.getSocialInsurNotiCreateSet().get().getOutputFormat(),
                model.getSocialInsurNotiCreateSet().get().getLineFeedCode()

        );
        if (!socialInsurNotiCreateSet.isPresent()) {
            mSocialInsurNotiCrSetRepository.add(input);
            return;
        }
        input.setInsuredNumber(model.getSocialInsurNotiCreateSet().get().getInsuredNumber());
        mSocialInsurNotiCrSetRepository.update(input);
        return;
    }

    private void checkAcquiNotiInsurProcess(List<String> employeeIds, GeneralDate startDate, GeneralDate endDate) {
        List<EmplHealInsurQualifiInfor> empExistHeal = new ArrayList<>();
        /* startDate > endDate*/
        if (startDate.after(endDate)) {
            throw new BusinessException("Msg_812");
        }
        /*アルゴリズム「社員健康保険資格情報」を取得する*/
        List<EmplHealInsurQualifiInfor> emplHealInsurQualifiInfors = emplHealInsurQualifiInforRepository.getAllEmplHealInsurQualifiInfor();
        EmplHealInsurQualifiInfor temp;
        for (int i = 0; i < employeeIds.size(); i++) {
            int finalI = i;
            temp = emplHealInsurQualifiInfors.stream().filter(e -> e.getEmployeeId().equals(employeeIds.get(finalI))).findFirst().get();
            boolean checkHeal = temp.getMourPeriod().stream().filter(e -> startDate.beforeOrEquals(e.getDatePeriod().start()) && endDate.afterOrEquals(e.getDatePeriod().end())).findFirst().isPresent();
            if (checkHeal) {
                empExistHeal.add(temp);
            }
        }

        //アルゴリズム「社員厚生年金保険資格情報」を取得する
        List<EmpWelfarePenInsQualiInfor> empExistWelfare = new ArrayList<>();
        List<EmpWelfarePenInsQualiInfor> empWelfarePenInsQualiInfors = mEmpWelfarePenInsQualiInforRepository.getAllEmpWelfarePenInsQualiInfor();
        EmpWelfarePenInsQualiInfor tempEmpWelfare;
        for (int i = 0; i < employeeIds.size(); i++) {
            int finalI = i;
            tempEmpWelfare = empWelfarePenInsQualiInfors.stream().filter(e -> e.getEmployeeId().equals(employeeIds.get(finalI))).findFirst().get();
            boolean checkHeal = tempEmpWelfare.getMournPeriod().stream().filter(e -> startDate.beforeOrEquals(e.getDatePeriod().start()) && endDate.afterOrEquals(e.getDatePeriod().end())).findFirst().isPresent();
            if (checkHeal) {
                empExistWelfare.add(tempEmpWelfare);
            }
        }


    }

    private void insurQualiNotiProcess(List<String> employeeIds, GeneralDate startDate, GeneralDate endDate) {
        final int DO_NOT_OUTPUT = 3;
        final int OUTPUT_BASIC_PER_NUMBER = 1;
        final int OUTPUT_COMPANY_NAME_BusinessDivision = 0;
        final int OUTPUT_SIC_INSURES_BusinessDivision = 1;
        final int DO_NOT_OUTPUT_BusinessDivision = 2;
        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();
        Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSet = mSocialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(uid, cid);
        int valuePrintPersonNumber = socialInsurNotiCreateSet.get().getPrintPersonNumber().value;
        int valueofficeInformation = socialInsurNotiCreateSet.get().getOfficeInformation().value;
        if (valuePrintPersonNumber == DO_NOT_OUTPUT || valuePrintPersonNumber == OUTPUT_BASIC_PER_NUMBER) {
            switch (valueofficeInformation) {
                case OUTPUT_COMPANY_NAME_BusinessDivision: {
                    break;
                }
                case OUTPUT_SIC_INSURES_BusinessDivision: {
                    break;
                }
                case DO_NOT_OUTPUT_BusinessDivision: {
                    break;
                }
            }
            //



            employeeIds.forEach(e -> {
                GuaByTheInsurExportData resulfExportData  = new GuaByTheInsurExportData();


                Optional<EmpHealthInsurBenefits> checkEmpHealthInsurBenefits = mEmplHealInsurQualifiInforRepository.getAllEmplHealInsurQualifiInfor().stream()
                        .filter(a -> a.getEmployeeId().equals(e))
                        .findFirst().get().getMourPeriod().stream()
                        .filter(b -> startDate.beforeOrEquals(b.getDatePeriod().start()) && endDate.afterOrEquals(b.getDatePeriod().end()))
                        .findFirst();

                if(checkEmpHealthInsurBenefits.isPresent()){

                    //update checkEmpHealthInsurBenefits ( ワークモデル「取得届情報」を更新する )


                    Optional<DateHistoryItem> mDateHistoryItem = mEmpCorpHealthOffHisRepository.getAllEmpCorpHealthOffHis().stream()
                            .filter(d -> d.getEmployeeId().equals(e)).findFirst()
                            .get()
                            .getPeriod()
                            .stream()
                            .filter(f -> startDate.beforeOrEquals(f.start()) && endDate.afterOrEquals(f.end()))
                            .findFirst();

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
                            .filter(g ->g.getEmployeeId().equals(e) &&  startDate.beforeOrEquals(g.getDatePeriod().start()) && endDate.afterOrEquals(g.getDatePeriod().end()))
                            .findFirst();

                    Optional<SocialInsurAcquisiInfor> socialInsurAcquisiInfor = mSocialInsurAcquisiInforRepository.getSocialInsurAcquisiInforById(e);


                    //check
                    if((resulfExportData.getHealInsurAcquiDate() != null && resulfExportData.getEmpPensionInsurAcquiDate() != null) && (resulfExportData.getHealInsurAcquiDate() != resulfExportData.getEmpPensionInsurAcquiDate()) && socialInsurAcquisiInfor.get().getPercentOrMore().get()== 1){
                        // update  checkEmployWelPenInsurAche 取得届情報
                    }
                    if(resulfExportData.getHealInsurAcquiDate() == null && resulfExportData.getEmpPensionInsurAcquiDate() != null && ( (resulfExportData.getHealInsurAcquiDate() != resulfExportData.getEmpPensionInsurAcquiDate()) && socialInsurAcquisiInfor.get().getPercentOrMore().get()== 1)){
                        // update  checkEmployWelPenInsurAche 取得届情報
                    }
                    {
                        // bước check nhưng chưa có EA
                    }



                }

            });


        }
    }
}
