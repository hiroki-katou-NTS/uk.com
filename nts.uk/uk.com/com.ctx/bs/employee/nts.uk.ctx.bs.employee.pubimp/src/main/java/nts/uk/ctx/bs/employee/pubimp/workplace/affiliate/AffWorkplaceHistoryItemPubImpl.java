package nts.uk.ctx.bs.employee.pubimp.workplace.affiliate;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.pub.workplace.affiliate.AffWorkplaceHistoryItemExport;
import nts.uk.ctx.bs.employee.pub.workplace.affiliate.AffWorkplaceHistoryItemPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class AffWorkplaceHistoryItemPubImpl implements AffWorkplaceHistoryItemPub {

    @Inject
    private AffWorkplaceHistoryItemRepository repo;

    @Override
    public List<AffWorkplaceHistoryItemExport> getListAffWkpHistItem(DatePeriod period, List<String> workplaceId) {
        List<AffWorkplaceHistoryItem> historyItems = repo.getAffWkpHistItemByListWkpIdAndDatePeriod(period, workplaceId);
        return historyItems.stream().map(x -> {
            return new AffWorkplaceHistoryItemExport(
                x.getHistoryId(),
                x.getEmployeeId(),
                x.getWorkplaceId(),
                x.getNormalWorkplaceId(),
                x.getWorkLocationCode().isPresent() ? Optional.of(x.getWorkLocationCode().get().v()) : Optional.empty());
        }).collect(Collectors.toList());
    }
}
