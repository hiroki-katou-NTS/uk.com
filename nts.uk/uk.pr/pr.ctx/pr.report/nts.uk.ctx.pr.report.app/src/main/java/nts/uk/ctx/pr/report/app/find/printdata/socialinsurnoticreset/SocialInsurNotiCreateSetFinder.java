package nts.uk.ctx.pr.report.app.find.printdata.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.InsurPersonNumDivision;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.SocialInsurNotiCrSetRepository;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SocialInsurNotiCreateSetFinder {

    @Inject
    private SocialInsurNotiCrSetRepository mSocialInsurNotiCrSetRepository;


    @Inject
    private SalGenParaIdentificationRepository mSalGenParaIdentificationRepository;

    @Inject
    private SalGenParaDateHistoryService mSalGenParaDateHistFinder;

    @Inject
    private SalGenParaYearMonthHistoryService mSalGenParaYMHistFinder;

    @Inject
    private SalGenParaYMHistRepository mSalGenParaYMHistRepository;

    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    @Inject
    private EmpWelfarePenInsQualiInforRepository mEmpWelfarePenInsQualiInforRepository;





    public SocialInsurNotiCreateSetDto initScreen(GeneralDate targetDate) {
        SocialInsurNotiCreateSetDto resulf = new SocialInsurNotiCreateSetDto();
        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();
        final int DATE_HISTORY = 0;
        /*パラメータNo = “KS0002”*/
        String paraNo = "KS002";
        resulf.setSocialInsurNotiCreateSet(mSocialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(uid, cid));


        ParaHistoryAtr historyAtr = mSalGenParaIdentificationRepository.getSalGenParaIdentificationById(paraNo, cid).get().getHistoryAtr();
        String historyid;
        Optional<SalGenParaValue> mSalGenParaValue;
        if (historyAtr.value == DATE_HISTORY) {
            /*開始日≦対象年月日≦終了日*/
            historyid = mSalGenParaDateHistFinder.getHistoryIdByTargetDate(paraNo, targetDate);
            mSalGenParaValue = mSalGenParaYMHistRepository.getSalGenParaValueById(historyid);
            if (!mSalGenParaValue.isPresent()) {
                /*開始日 = 最も古い開始日の履歴*/
                historyid = mSalGenParaDateHistFinder.getHistoryIdByStartDate(paraNo, targetDate);
                mSalGenParaValue = mSalGenParaYMHistRepository.getSalGenParaValueById(historyid);

            }
            resulf.setSalGenParaValue(mSalGenParaValue);
            return resulf;
        }
        historyid = mSalGenParaYMHistFinder.getHistoryIdByTargetDate(paraNo, targetDate);
        mSalGenParaValue = mSalGenParaYMHistRepository.getSalGenParaValueById(historyid);
        if (!mSalGenParaValue.isPresent()) {
            historyid = mSalGenParaYMHistFinder.getHistoryIdByStartDate(paraNo);
            mSalGenParaValue = mSalGenParaYMHistRepository.getSalGenParaValueById(historyid);
        }
        resulf.setSalGenParaValue(mSalGenParaValue);
        return resulf;
    }

    public void printInsuredQualifiNoti(List<String> employeeIds,SocialInsurNotiCreateSetDto model,GeneralDate startDate, GeneralDate endDate) {
        settingRegisProcess(model);
        checkAcquiNotiInsurProcess(employeeIds,startDate,endDate);
        insurQualiNotiProcess(employeeIds);
    }

    private void settingRegisProcess(SocialInsurNotiCreateSetDto model) {

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
        List<EmpWelfarePenInsQualiInfor>  empWelfarePenInsQualiInfors = mEmpWelfarePenInsQualiInforRepository.getAllEmpWelfarePenInsQualiInfor();
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

    private void insurQualiNotiProcess(List<String> employeeIds){
        final int DO_NOT_OUTPUT = 3;
        final int OUTPUT_BASIC_PER_NUMBER = 1;
        final int OUTPUT_COMPANY_NAME_BusinessDivision = 0;
        final int OUTPUT_SIC_INSURES_BusinessDivision = 1;
        final int DO_NOT_OUTPUT_BusinessDivision = 2;
        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();
        Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSet =  mSocialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(uid,cid);
        int valuePrintPersonNumber = socialInsurNotiCreateSet.get().getPrintPersonNumber().value ;
        int valueofficeInformation = socialInsurNotiCreateSet.get().getOfficeInformation().value ;
        if(valuePrintPersonNumber == DO_NOT_OUTPUT ||valuePrintPersonNumber == OUTPUT_BASIC_PER_NUMBER){
            switch (valueofficeInformation){
                case OUTPUT_COMPANY_NAME_BusinessDivision :{
                    break;
                }
                case OUTPUT_SIC_INSURES_BusinessDivision :
                {
                    break;
                }
                case DO_NOT_OUTPUT_BusinessDivision:{
                    break;
                }
            }
            //
            employeeIds.stream().forEach(e -> {

            });

        }
    }
}
