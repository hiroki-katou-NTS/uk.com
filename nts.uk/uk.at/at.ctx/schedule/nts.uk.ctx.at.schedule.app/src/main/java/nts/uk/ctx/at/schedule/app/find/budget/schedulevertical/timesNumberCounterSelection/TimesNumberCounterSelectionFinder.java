package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.timesNumberCounterSelection;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterSelection;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterSelectionRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterType;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 回数集計選択を取得する
 */
@Stateless
public class TimesNumberCounterSelectionFinder {

    @Inject
    private TimesNumberCounterSelectionRepo repository;

    public SelectNoListDto findById(int countType) {
        Optional<TimesNumberCounterSelection> timesNumberCounterSelection =
            repository.get(AppContexts.user().companyId(), EnumAdaptor.valueOf(countType, TimesNumberCounterType.class));

        return timesNumberCounterSelection.map(timesNumberCounterSelection1 -> new SelectNoListDto(timesNumberCounterSelection1.getSelectedNoList())).orElseGet(SelectNoListDto::new);
    }
}
