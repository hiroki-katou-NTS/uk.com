package nts.uk.ctx.bs.employee.pubimp.workplace.affiliate;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistory;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.pub.workplace.affiliate.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class AffHistoryPubImpl implements AffHistoryPub {

    @Inject
    private AffWorkplaceHistoryItemRepository historyItemRepository;

    @Inject
    private AffJobTitleHistoryRepository jobTitleHistoryRepository;

    @Override
    public AffHistoryExport getAffWkpHist(GeneralDate baseDate, List<String> employeeIds) {
        List<AffJobTitleHistory> findAllJobTitleHistory = jobTitleHistoryRepository.findAllJobTitleHistory(baseDate, employeeIds);

        List<String> histIds = new ArrayList<>();
        findAllJobTitleHistory.forEach(x -> {
            x.getHistoryItems().stream().map(i -> histIds.add(i.identifier()));
        });

        List<AffWorkplaceHistoryItem> historyItems = historyItemRepository.findByHistIds(histIds);

        return new AffHistoryExport(
            findAllJobTitleHistory.stream().map(x -> {
                return new AffJobTitleHistoryExport(
                    x.getCompanyId(),
                    x.getEmployeeId(),
                    x.getHistoryItems().stream().map(i -> {
                        return new DateHistoryItemExport(i.identifier(), i.span());
                    }).collect(Collectors.toList())
                );
            }).collect(Collectors.toList()),
            historyItems.stream().map(x -> {
                return new AffWorkplaceHistoryItemExport(
                    x.getHistoryId(),
                    x.getEmployeeId(),
                    x.getWorkplaceId(),
                    x.getNormalWorkplaceId(),
                    x.getWorkLocationCode().isPresent() ? Optional.of(x.getWorkLocationCode().get().v()) : Optional.empty());
            }).collect(Collectors.toList())
        );
    }
}
