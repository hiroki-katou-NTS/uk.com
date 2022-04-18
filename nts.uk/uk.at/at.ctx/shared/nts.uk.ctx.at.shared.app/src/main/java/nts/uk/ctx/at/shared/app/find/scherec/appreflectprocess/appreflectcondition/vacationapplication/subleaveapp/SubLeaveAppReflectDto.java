package nts.uk.ctx.at.shared.app.find.scherec.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp.SubstituteLeaveAppReflect;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubLeaveAppReflectDto {
    private int reflectWorkHour;
    private int oneDayLeaveDeleteAttendance;

    public static SubLeaveAppReflectDto fromDomain(SubstituteLeaveAppReflect domain) {
        return new SubLeaveAppReflectDto(
                domain.getWorkInfoAttendanceReflect().getReflectWorkHour().value,
                domain.getWorkInfoAttendanceReflect().getOneDayLeaveDeleteAttendance().value
        );
    }
}
