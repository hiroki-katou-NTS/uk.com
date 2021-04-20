package nts.uk.ctx.at.function.ac.dailyperform;

import lombok.Setter;
import nts.uk.ctx.at.function.dom.adapter.dailyperform.DailyPerformanceAuthorityAdapter;
import nts.uk.ctx.at.function.dom.adapter.dailyperform.DailyPerformanceAuthorityDto;
import nts.uk.ctx.at.record.pub.dailyperform.DailyPerformAuthorPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class DailyPerformAuthorAdapterImpl implements DailyPerformanceAuthorityAdapter {
    @Inject
    DailyPerformAuthorPub dailyPerformAuthorPub;
    @Override
    public List<DailyPerformanceAuthorityDto> get(String roleId) {
        return dailyPerformAuthorPub.get(roleId).stream()
                .map(e->new DailyPerformanceAuthorityDto(
                        e.getCompanyId(),
                        e.getRoleID(),
                        e.getFunctionNo().v(),
                        e.isAvailability()))
                .collect(Collectors.toList());
    }
}
