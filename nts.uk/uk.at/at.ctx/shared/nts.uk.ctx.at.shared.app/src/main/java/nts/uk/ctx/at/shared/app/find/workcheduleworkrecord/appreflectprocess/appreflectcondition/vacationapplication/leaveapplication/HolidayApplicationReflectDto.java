package nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectConditionDto;
import nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOptionDto;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.ReflectWorkHourCondition;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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
                        domain.getWorkAttendanceReflect().getReflectAttendance().value,
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
