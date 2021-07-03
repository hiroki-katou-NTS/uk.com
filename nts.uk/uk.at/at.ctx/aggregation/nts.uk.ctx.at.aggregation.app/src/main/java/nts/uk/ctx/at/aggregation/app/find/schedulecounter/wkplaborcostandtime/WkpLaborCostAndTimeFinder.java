package nts.uk.ctx.at.aggregation.app.find.schedulecounter.wkplaborcostandtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.laborcostandtime.WorkplaceCounterLaborCostAndTime;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.laborcostandtime.WorkplaceCounterLaborCostAndTimeRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 職場計の人件費・時間情報を取得する
 */
@Stateless
public class WkpLaborCostAndTimeFinder {

    @Inject
    private WorkplaceCounterLaborCostAndTimeRepo repository;

    public List<WkpLaborCostAndTimeDto> findById() {
        Optional<WorkplaceCounterLaborCostAndTime> wkpLaborCostAndTime = repository.get(AppContexts.user().companyId());
        return wkpLaborCostAndTime.isPresent() ? WkpLaborCostAndTimeDto.setData(wkpLaborCostAndTime.get()) : new ArrayList<>();
    }
}
