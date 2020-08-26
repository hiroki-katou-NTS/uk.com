package nts.uk.screen.at.app.ksm005.find;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;

import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class MonthlyPatternDto {

    //List<月間パターンの勤務情報>
    private List<WorkMonthlySetting> workMonthlySettings;

    //List<勤務種類名称>
    private List<String> workTypeName;

    //List<就業時間帯名称>
    private List<String> workTimeName;

    //Optional<出勤休日区分>
    private List<WorkStyle> workStyles;

    private List<String> listMonthYear;


}
