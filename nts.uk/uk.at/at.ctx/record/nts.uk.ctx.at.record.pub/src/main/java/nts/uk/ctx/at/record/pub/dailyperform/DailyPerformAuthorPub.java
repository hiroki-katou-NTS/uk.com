package nts.uk.ctx.at.record.pub.dailyperform;

import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformanceAuthority;

import java.util.List;

public interface DailyPerformAuthorPub {
    public List<DailyPerformanceAuthority> get(String roleId);
}
