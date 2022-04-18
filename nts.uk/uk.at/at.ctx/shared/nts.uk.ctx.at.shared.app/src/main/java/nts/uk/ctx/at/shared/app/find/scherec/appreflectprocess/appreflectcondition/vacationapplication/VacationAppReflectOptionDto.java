package nts.uk.ctx.at.shared.app.find.scherec.appreflectprocess.appreflectcondition.vacationapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.ReflectWorkHourCondition;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacationAppReflectOptionDto {
    /**
     * 1日休暇の場合は出退勤を削除
     */
    private int oneDayLeaveDeleteAttendance;

    /**
     * 就業時間帯を反映する
     */
    private int reflectWorkHour;
    
    public static VacationAppReflectOptionDto fromDomain(VacationAppReflectOption domain) {
    	return new VacationAppReflectOptionDto(domain.getOneDayLeaveDeleteAttendance().value, domain.getReflectWorkHour().value);
    }
    
    public VacationAppReflectOption toDomain() {
        return new VacationAppReflectOption(
                EnumAdaptor.valueOf(oneDayLeaveDeleteAttendance, NotUseAtr.class), 
                EnumAdaptor.valueOf(reflectWorkHour, ReflectWorkHourCondition.class));
    }
}
