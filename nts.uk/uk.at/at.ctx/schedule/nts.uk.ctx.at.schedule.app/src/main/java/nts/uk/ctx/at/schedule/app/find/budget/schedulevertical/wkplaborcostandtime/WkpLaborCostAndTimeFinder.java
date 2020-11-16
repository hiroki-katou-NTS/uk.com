package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.wkplaborcostandtime;

import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTime;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTimeRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 職場計の人件費・時間情報を取得する
 */
@Stateless
public class WkpLaborCostAndTimeFinder {

    @Inject
    private WorkplaceCounterLaborCostAndTimeRepo repository;

    public List<WkpLaborCostAndTimeDto> findById() {
        Optional<WorkplaceCounterLaborCostAndTime> wkpLaborCostAndTime = repository.get(AppContexts.user().companyId());
        return WkpLaborCostAndTimeDto.setData(wkpLaborCostAndTime);
    }
}
