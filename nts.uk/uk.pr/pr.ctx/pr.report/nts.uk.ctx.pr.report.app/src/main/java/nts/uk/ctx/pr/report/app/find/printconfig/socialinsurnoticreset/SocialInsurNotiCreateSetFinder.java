package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCrSetRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class SocialInsurNotiCreateSetFinder {

    @Inject
    private SocialInsurNotiCrSetRepository socialInsurNotiCrSetRepository;

    @Inject
    private SalGenParaIdentificationRepository mSalGenParaIdentificationRepository;

    @Inject
    private SalGenParaDateHistoryService mSalGenParaDateHistFinder;

    @Inject
    private SalGenParaYearMonthHistoryService mSalGenParaYMHistFinder;

    @Inject
    private SalGenParaYMHistRepository mSalGenParaYMHistRepository;

    @Inject
    private SalGenParaDateHistRepository mSalGenParaDateHistRepository;

    public SocialInsurNotiCreateSetDto getSocInsurNotiCreSet() {
        String cid = AppContexts.user().companyId();
        String userId = AppContexts.user().userId();
        Optional<SocialInsurNotiCreateSet> socialInsurNotiCreateSet = socialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(userId, cid);
        return socialInsurNotiCreateSet.isPresent() ? SocialInsurNotiCreateSetDto.fromDomain(socialInsurNotiCreateSet.get()) : null;
    }


    public NotifiOfInsurQuaAcDto initScreen(GeneralDate targetDate) {
        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();
        final int DATE_HISTORY = 0;
        //*パラメータNo = “KS0002”*//*
        String paraNo = "KS002";
        Optional<SocialInsurNotiCreateSet> mSocialInsurNotiCreateSet = socialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(uid, cid);
        if(!mSocialInsurNotiCreateSet.isPresent()){
            mSocialInsurNotiCreateSet = Optional.of(new SocialInsurNotiCreateSet("",
                    "",
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    "",
                    0,
                    0,
                    0
            ));
        }
        ParaHistoryAtr historyAtr = mSalGenParaIdentificationRepository.getSalGenParaIdentificationById(paraNo, cid).get().getHistoryAtr();
        String historyid;
        Optional<SalGenParaValue> mSalGenParaValue;
        if (historyAtr.value == DATE_HISTORY) {
            //*開始日≦対象年月日≦終了日*//*
            historyid = mSalGenParaDateHistFinder.getHistoryIdByTargetDate(paraNo, targetDate);
            if(!historyid.equals("")){
                mSalGenParaValue = mSalGenParaDateHistRepository.getSalGenParaValueById(historyid);
                if (!mSalGenParaValue.isPresent()) {
                    //*開始日 = 最も古い開始日の履歴*//*
                    historyid = mSalGenParaDateHistFinder.getHistoryIdByStartDate(paraNo, targetDate);
                    mSalGenParaValue = mSalGenParaDateHistRepository.getSalGenParaValueById(historyid);

                }
                NotifiOfInsurQuaAcDto.fromDomain( mSocialInsurNotiCreateSet.get(),mSalGenParaValue.get());

            }

            return NotifiOfInsurQuaAcDto.fromDomain( mSocialInsurNotiCreateSet.get(), null);

        }
        historyid = mSalGenParaYMHistFinder.getHistoryIdByTargetDate(paraNo, targetDate);
        if(!historyid.equals("")){
            mSalGenParaValue = mSalGenParaYMHistRepository.getSalGenParaValueById(historyid);
            if (!mSalGenParaValue.isPresent()) {
                historyid = mSalGenParaYMHistFinder.getHistoryIdByStartDate(paraNo);
                mSalGenParaValue = mSalGenParaYMHistRepository.getSalGenParaValueById(historyid);
            }
            return  NotifiOfInsurQuaAcDto.fromDomain(mSocialInsurNotiCreateSet.get(), mSalGenParaValue.get());

        }
        return NotifiOfInsurQuaAcDto.fromDomain(mSocialInsurNotiCreateSet.get(), null);


    }


}
