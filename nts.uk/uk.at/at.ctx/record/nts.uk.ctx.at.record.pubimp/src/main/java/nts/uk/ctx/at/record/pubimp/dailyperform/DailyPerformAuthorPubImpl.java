package nts.uk.ctx.at.record.pubimp.dailyperform;

import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformAuthorRepo;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformanceAuthority;
import nts.uk.ctx.at.record.pub.dailyperform.DailyPerformAuthorPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class DailyPerformAuthorPubImpl implements DailyPerformAuthorPub {

    @Inject
    private DailyPerformAuthorRepo repo;
    @Override
    public List<DailyPerformanceAuthority> get(String roleId) {
        return repo.get(roleId);
    }
}
