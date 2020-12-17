package nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectConditionCommand;
import nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOptionCommand;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.ReflectWorkHourCondition;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayApplicationReflectCommand {

    private VacationAppReflectOptionCommand workAttendanceReflect;

    private TimeLeaveAppReflectConditionCommand timeLeaveReflect;

    public VacationApplicationReflect toDomain(String companyId) {
        return new VacationApplicationReflect(
                companyId,
                new VacationAppReflectOption(
                        EnumAdaptor.valueOf(workAttendanceReflect.getOneDayLeaveDeleteAttendance(), NotUseAtr.class),
                        EnumAdaptor.valueOf(workAttendanceReflect.getReflectAttendance(), NotUseAtr.class),
                        EnumAdaptor.valueOf(workAttendanceReflect.getReflectWorkHour(), ReflectWorkHourCondition.class)
                ),
                new TimeLeaveAppReflectCondition(
                        EnumAdaptor.valueOf(timeLeaveReflect.getSuperHoliday60H(), NotUseAtr.class),
                        EnumAdaptor.valueOf(timeLeaveReflect.getNursing(), NotUseAtr.class),
                        EnumAdaptor.valueOf(timeLeaveReflect.getChildNursing(), NotUseAtr.class),
                        EnumAdaptor.valueOf(timeLeaveReflect.getSubstituteLeaveTime(), NotUseAtr.class),
                        EnumAdaptor.valueOf(timeLeaveReflect.getAnnualVacationTime(), NotUseAtr.class),
                        EnumAdaptor.valueOf(timeLeaveReflect.getSpecialVacationTime(), NotUseAtr.class)
                )
        );
    }

    public static HolidayApplicationReflectCommand fromDomain(VacationApplicationReflect vacationApplicationReflect) {
        return new HolidayApplicationReflectCommand(
                VacationAppReflectOptionCommand.fromDomain(vacationApplicationReflect.getWorkAttendanceReflect()), 
                TimeLeaveAppReflectConditionCommand.fromDomain(vacationApplicationReflect.getTimeLeaveReflect())
                );
    }
}
