package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.wkpCounterLaborCostAndTime;

import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTime;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTimeRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 職場計の人件費・時間情報を取得する
 */
public class WkpLaborCostAndTimeFinder {

    @Inject
    private WorkplaceCounterLaborCostAndTimeRepo repository;

    public List<WkpLaborCostAndTimeDto> findById() {

        Optional<WorkplaceCounterLaborCostAndTime> wkpLaborCostAndTime = repository.get(AppContexts.user().companyId());
        if (!wkpLaborCostAndTime.isPresent()) {
            return new ArrayList<>();
        }
        return WkpLaborCostAndTimeDto.setData(wkpLaborCostAndTime.get());
    }
}
