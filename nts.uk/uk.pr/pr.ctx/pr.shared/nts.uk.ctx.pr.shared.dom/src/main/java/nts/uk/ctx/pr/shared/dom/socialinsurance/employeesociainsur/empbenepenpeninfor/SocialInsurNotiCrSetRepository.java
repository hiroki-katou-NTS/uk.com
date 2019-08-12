package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import java.util.List;
import java.util.Optional;

public interface SocialInsurNotiCrSetRepository {
    List<SocialInsurNotiCreateSet> getAllSocialInsurNotiCreateSet();

    Optional<SocialInsurNotiCreateSet> getSocialInsurNotiCreateSetById(String userId, String cid);

    void add(SocialInsurNotiCreateSet domain);

    void update(SocialInsurNotiCreateSet domain);

    void remove(String userId, String cid);
}
