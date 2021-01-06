package nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacationAppReflectOptionDto {
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
    
    public static VacationAppReflectOptionDto fromDomain(VacationAppReflectOption domain) {
    	return new VacationAppReflectOptionDto(domain.getOneDayLeaveDeleteAttendance().value, domain.getReflectAttendance().value, domain.getReflectWorkHour().value);
    }
}
