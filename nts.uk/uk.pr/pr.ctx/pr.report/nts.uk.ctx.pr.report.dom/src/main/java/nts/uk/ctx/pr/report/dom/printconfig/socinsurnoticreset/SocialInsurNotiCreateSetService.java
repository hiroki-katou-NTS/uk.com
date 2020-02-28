package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;


import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class SocialInsurNotiCreateSetService {

    @Inject
    private SocialInsurNotiCrSetRepository socialInsurNotiCrSetRepository;

    public void checkSocialInsurNotiCrSet(SocialInsurNotiCreateSet domain, String userId, String cid){
        Optional<SocialInsurNotiCreateSet>  socialInsurNotiCreateSet = socialInsurNotiCrSetRepository.getSocialInsurNotiCreateSetById(userId,cid);

        if(socialInsurNotiCreateSet.isPresent()){
            socialInsurNotiCrSetRepository.update(domain);
        }else{
            socialInsurNotiCrSetRepository.add(domain);
        }

    }
}
