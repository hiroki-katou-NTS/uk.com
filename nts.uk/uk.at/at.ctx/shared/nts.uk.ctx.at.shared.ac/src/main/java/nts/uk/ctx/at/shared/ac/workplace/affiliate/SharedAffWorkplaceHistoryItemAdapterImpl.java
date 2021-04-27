package nts.uk.ctx.at.shared.ac.workplace.affiliate;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.workplace.affiliate.AffWorkplaceHistoryItemImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.affiliate.SharedAffWorkplaceHistoryItemAdapter;
import nts.uk.ctx.bs.employee.pub.workplace.affiliate.AffWorkplaceHistoryItemPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SharedAffWorkplaceHistoryItemAdapterImpl implements SharedAffWorkplaceHistoryItemAdapter {

    @Inject
    private AffWorkplaceHistoryItemPub affWorkplaceHistoryItemPub;

    @Override
    public List<AffWorkplaceHistoryItemImport> getListAffWkpHistItem(DatePeriod period, List<String> workplaceId) {
        return affWorkplaceHistoryItemPub.getListAffWkpHistItem(period, workplaceId).stream().map(x ->
                new AffWorkplaceHistoryItemImport(
                        x.getHistoryId(),
                        x.getEmployeeId(),
                        x.getWorkplaceId(),
                        x.getNormalWorkplaceId(),
                        x.getWorkLocationCode()
                )).collect(Collectors.toList());
    }
}
