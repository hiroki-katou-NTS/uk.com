package nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacationAppReflectOptionCommand {
    /**
     * 1日休暇の場合は出退勤を削除
     */
    private int oneDayLeaveDeleteAttendance;

    /**
     * 出退勤を反映する
     */
    private int reflectAttendance;

    /**
     * 就業時間帯を反映する
     */
    private int reflectWorkHour;
}
