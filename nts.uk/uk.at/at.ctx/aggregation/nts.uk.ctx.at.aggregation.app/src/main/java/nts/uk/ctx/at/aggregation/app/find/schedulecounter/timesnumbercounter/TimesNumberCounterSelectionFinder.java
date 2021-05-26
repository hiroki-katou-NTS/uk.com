package nts.uk.ctx.at.aggregation.app.find.schedulecounter.timesnumbercounter;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timescounting.TimesNumberCounterSelection;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timescounting.TimesNumberCounterSelectionRepo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timescounting.TimesNumberCounterType;
import nts.uk.shr.com.context.AppContexts;

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
