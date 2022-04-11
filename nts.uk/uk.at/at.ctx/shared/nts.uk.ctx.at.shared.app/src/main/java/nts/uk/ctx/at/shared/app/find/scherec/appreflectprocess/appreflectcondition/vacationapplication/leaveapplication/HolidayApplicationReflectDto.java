package nts.uk.ctx.at.shared.app.find.scherec.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.find.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectConditionDto;
import nts.uk.ctx.at.shared.app.find.scherec.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOptionDto;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayApplicationReflectDto {

    private VacationAppReflectOptionDto workAttendanceReflect;

    private TimeLeaveAppReflectConditionDto timeLeaveReflect;

    public static HolidayApplicationReflectDto fromDomain(VacationApplicationReflect domain) {
        return new HolidayApplicationReflectDto(
                new VacationAppReflectOptionDto(
                        domain.getWorkAttendanceReflect().getOneDayLeaveDeleteAttendance().value,
                        domain.getWorkAttendanceReflect().getReflectWorkHour().value
                ),
                new TimeLeaveAppReflectConditionDto(
                        domain.getTimeLeaveReflect().getSuperHoliday60H().value,
                        domain.getTimeLeaveReflect().getNursing().value,
                        domain.getTimeLeaveReflect().getChildNursing().value,
                        domain.getTimeLeaveReflect().getSubstituteLeaveTime().value,
                        domain.getTimeLeaveReflect().getAnnualVacationTime().value,
                        domain.getTimeLeaveReflect().getSpecialVacationTime().value
                )
        );
    }

}
