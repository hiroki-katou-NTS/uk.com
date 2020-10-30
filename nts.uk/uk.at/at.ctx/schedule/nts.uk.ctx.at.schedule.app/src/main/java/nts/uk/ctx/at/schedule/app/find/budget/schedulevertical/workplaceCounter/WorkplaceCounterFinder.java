package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.workplaceCounter;

import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounter;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounterRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * スケジュール職場計情報を取得する
 */
public class WorkplaceCounterFinder {
    @Inject
    private WorkplaceCounterRepo repository;

    public List<WorkplaceCounterCategoryDto> findById() {

        Optional<WorkplaceCounter> workplaceCounter = repository.get(AppContexts.user().companyId());
        return workplaceCounter.isPresent() ? WorkplaceCounterCategoryDto.setData(workplaceCounter.get()) : new ArrayList<>();
    }
}

