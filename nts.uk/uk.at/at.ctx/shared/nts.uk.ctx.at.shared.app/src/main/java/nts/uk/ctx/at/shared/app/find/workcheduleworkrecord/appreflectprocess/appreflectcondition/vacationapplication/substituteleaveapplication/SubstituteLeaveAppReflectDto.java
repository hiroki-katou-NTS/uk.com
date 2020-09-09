package nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.substituteleaveapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.substituteleaveapplication.SubstituteLeaveAppReflect;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubstituteLeaveAppReflectDto {
    private int reflectWorkHour;
    private int reflectAttendance;
    private int oneDayLeaveDeleteAttendance;

    public static SubstituteLeaveAppReflectDto fromDomain(SubstituteLeaveAppReflect domain) {
        return new SubstituteLeaveAppReflectDto(
                domain.getWorkInfoAttendanceReflect().getReflectWorkHour().value,
                domain.getWorkInfoAttendanceReflect().getReflectAttendance().value,
                domain.getWorkInfoAttendanceReflect().getOneDayLeaveDeleteAttendance().value
        );
    }
}
