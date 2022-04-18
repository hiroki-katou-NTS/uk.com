package nts.uk.ctx.at.shared.app.command.scherec.appreflectprocess.appreflectcondition.vacationapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacationAppReflectOptionCommand {
    /**
     * 1日休暇の場合は出退勤を削除
     */
    private int oneDayLeaveDeleteAttendance;

    /**
     * 就業時間帯を反映する
     */
    private int reflectWorkHour;
    
    public static VacationAppReflectOptionCommand fromDomain(VacationAppReflectOption vacationAppReflectOption) {
        return new VacationAppReflectOptionCommand(
                vacationAppReflectOption.getOneDayLeaveDeleteAttendance().value, 
                vacationAppReflectOption.getReflectWorkHour().value);
    }
}
