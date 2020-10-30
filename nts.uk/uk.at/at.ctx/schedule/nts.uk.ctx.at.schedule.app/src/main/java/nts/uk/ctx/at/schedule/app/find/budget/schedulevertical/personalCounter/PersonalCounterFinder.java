package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.personalCounter;

import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounter;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounterRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * スケジュール個人計情報を取得する
 */
public class PersonalCounterFinder {
    @Inject
    private PersonalCounterRepo repository;

    public List<PersonalCounterCategoryDto> findById() {

        Optional<PersonalCounter> personalCounter = repository.get(AppContexts.user().companyId());
        return personalCounter.isPresent() ? PersonalCounterCategoryDto.setData(personalCounter.get()) : new ArrayList<>();
    }
}

