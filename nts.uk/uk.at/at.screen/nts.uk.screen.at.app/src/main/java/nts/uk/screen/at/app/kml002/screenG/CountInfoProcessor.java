package nts.uk.screen.at.app.kml002.screenG;

import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.timesNumberCounterSelection.SelectNoListDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.timesNumberCounterSelection.TimesNumberCounterSelectionFinder;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Screen KML002  G : 回数集計情報を取得する
 */
@Stateless
public class CountInfoProcessor {

    @Inject
    private TotalTimesRepository totalTimesRepository;

    @Inject
    private TimesNumberCounterSelectionFinder finder;

    public CountInfoDto getInfo(RequestPrams requestPrams) {

        List<TotalTimes> totalTimes = totalTimesRepository.getAllTotalTimes(AppContexts.user().companyId());
        totalTimes = totalTimes.stream().filter(x -> x.getUseAtr() == UseAtr.Use).collect(Collectors.toList());
        List<CountNumberOfTimeDto> countNumberOfTimeDtos = totalTimes.stream().map(x -> new CountNumberOfTimeDto(x.getTotalCountNo() ,x.getTotalTimesName().v())).collect(Collectors.toList());

        SelectNoListDto selectNoListDtos = finder.findById(requestPrams.getCountType());
        List<TotalTimes> totalTimesList = totalTimesRepository.getTotalTimesDetailByListNo(AppContexts.user().companyId(),selectNoListDtos.getSelectedNoList());

        List<NumberOfTimeTotalDto> numberOfTimeTotalDtos = totalTimesList.stream().map(x -> new NumberOfTimeTotalDto(x.getTotalCountNo() ,x.getTotalTimesName().v())).collect(Collectors.toList());

        return new CountInfoDto(countNumberOfTimeDtos,numberOfTimeTotalDtos);

    }
}
