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

        return NotifiOfInsurQuaAcDto.fromDomain(mSocialInsurNotiCreateSet.get(), null);


    }

    public SocialInsurNotiCreateSetDto getSocialInsurNotiCreateSetById(String userId, String cid){
        Optional<SocialInsurNotiCreateSet> domain = socialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(userId,cid);

        if(domain.isPresent()){
            return SocialInsurNotiCreateSetDto.fromDomain(domain.get());
        }

        return null;
    }


}
