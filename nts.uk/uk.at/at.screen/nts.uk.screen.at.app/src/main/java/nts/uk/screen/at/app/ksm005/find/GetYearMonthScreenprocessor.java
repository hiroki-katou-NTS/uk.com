package nts.uk.screen.at.app.ksm005.find;

import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 設定した年月一覧を取得する
 */

@Stateless
public class GetYearMonthScreenprocessor {

    @Inject
    private WorkMonthlySettingRepository workMonthlySettingRepository;


    public List<Integer> GetYearMonth(String cid, String monthlyPatternCode, int year) {
        List<WorkMonthlySetting> workMonthlySettings = workMonthlySettingRepository.findByYear(cid,monthlyPatternCode,year);
        if (workMonthlySettings == null || workMonthlySettings.isEmpty()){
            return  new ArrayList<>();
        }
        return  workMonthlySettings.stream().map(x -> x.getYmdk().yearMonth().month()).distinct().collect(Collectors.toList());
    }
}
