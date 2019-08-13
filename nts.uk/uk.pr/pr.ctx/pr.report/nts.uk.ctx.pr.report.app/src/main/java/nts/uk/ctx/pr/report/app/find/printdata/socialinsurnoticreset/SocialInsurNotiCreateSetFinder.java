package nts.uk.ctx.pr.report.app.find.printdata.socialinsurnoticreset;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.SocialInsurNotiCrSetRepository;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.*;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
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





    public GuaByTheInsurDto initScreen(GeneralDate targetDate) {
        GuaByTheInsurDto resulf = new GuaByTheInsurDto();
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


}
