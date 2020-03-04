package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import java.util.Optional;

public interface SocialInsurNotiCrSetRepository {

    Optional<SocialInsurNotiCreateSet> getSocialInsurNotiCreateSetById(String userId, String cid);

    void add(SocialInsurNotiCreateSet domain);

    void update(SocialInsurNotiCreateSet domain);

    void remove(String userId, String cid);
}
