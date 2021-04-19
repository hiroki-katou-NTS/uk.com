package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

import java.util.Optional;

/**
 * スケジュール月次の残数チェック
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ScheduleMonRemainCheckCond implements ScheduleMonCheckCond {
    // チェックする休暇
    private TypeOfVacations typeOfVacations;

    // 特別休暇
    private Optional<SpecialHolidayCode> specialHolidayCode;
}
