package nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset;

import java.util.List;
import java.util.Optional;

public interface SocialInsurNotiCrSetRepository {

    Optional<SocialInsurNotiCreateSet> getSocialInsurNotiCreateSetById(String userId, String cid);

    void add(SocialInsurNotiCreateSet domain);

    void update(SocialInsurNotiCreateSet domain);

    void remove(String userId, String cid);
}
