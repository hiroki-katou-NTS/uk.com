package nts.uk.screen.at.app.kml002.screenG;

import lombok.val;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.timesNumberCounterSelection.SelectNoListDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.timesNumberCounterSelection.TimesNumberCounterSelectionFinder;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
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

        //1:回数集計のマスタ情報を取得する(会社ID、利用区分) : List<回数集計>
        List<TotalTimes> totalTimes = totalTimesRepository.getAllTotalTimes(AppContexts.user().companyId()).stream().filter(x -> x.getUseAtr() == UseAtr.Use).collect(Collectors.toList());
        List<CountNumberOfTimeDto> countNumberOfTimeDtos = totalTimes.stream().map(x -> new CountNumberOfTimeDto(x.getTotalCountNo() ,x.getTotalTimesName().v())).collect(Collectors.toList());
        List<NumberOfTimeTotalDto> numberOfTimeTotalDtos = new ArrayList<>();

        //2: List<回数集計>.size > 0 : 取得する(会社ID, 回数集計種類) : Optional<回数集計選択>
        if (totalTimes. size() > 0){
            SelectNoListDto selectNoListDtos = finder.findById(requestPrams.getCountType());

            numberOfTimeTotalDtos.addAll(selectNoListDtos.getSelectedNoList()
                .stream()
                .map(x -> {
                    val totalTimesName = totalTimes.stream().filter(i -> i.getTotalCountNo().equals(x)).findFirst();
                    return new NumberOfTimeTotalDto(x, totalTimesName.isPresent() ? totalTimesName.get().getTotalTimesName().v() : "");
                }).collect(Collectors.toList()));
        }

        return new CountInfoDto(countNumberOfTimeDtos,numberOfTimeTotalDtos);

    }
}
