package nts.uk.screen.at.app.ksm005.find;

import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 設定した年月一覧を取得する
 */

@Stateless
public class GetYearMonthScreenprocessor {

    @Inject
    private WorkMonthlySettingRepository workMonthlySettingRepository;


    public List<String> GetYearMonth(String cid, String monthlyPatternCode, int year) {
        List<WorkMonthlySetting> workMonthlySettings = workMonthlySettingRepository.findByYear(cid,monthlyPatternCode,year);

        List<String> listMonthYear = new ArrayList<>();
        workMonthlySettings.stream().forEach(x -> listMonthYear.add(x.getYmdk().toString("yyyyMM")) );
        return listMonthYear;
    }
}
