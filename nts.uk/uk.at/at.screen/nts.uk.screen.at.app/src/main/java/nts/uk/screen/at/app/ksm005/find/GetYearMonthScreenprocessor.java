package nts.uk.screen.at.app.ksm005.find;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.Array;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        // Create period from yearmonth
        DatePeriod datePeriod = new DatePeriod(workMonthlySettings.get(0).getYmdk(),workMonthlySettings.get(workMonthlySettings.size()-1).getYmdk());
        return datePeriod.yearMonthsBetween().stream().map(i->i.month()).collect(Collectors.toList());
    }
}
