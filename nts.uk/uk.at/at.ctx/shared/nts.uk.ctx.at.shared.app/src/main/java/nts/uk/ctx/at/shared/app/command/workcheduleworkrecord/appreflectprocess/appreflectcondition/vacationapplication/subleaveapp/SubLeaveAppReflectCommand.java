package nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.ReflectWorkHourCondition;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp.SubstituteLeaveAppReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubLeaveAppReflectCommand {
    private int reflectWorkHour;
    private int reflectAttendance;
    private int oneDayLeaveDeleteAttendance;

    public SubstituteLeaveAppReflect toDomain() {
        return new SubstituteLeaveAppReflect(
                new VacationAppReflectOption(
                        EnumAdaptor.valueOf(oneDayLeaveDeleteAttendance, NotUseAtr.class),
                        EnumAdaptor.valueOf(reflectAttendance, NotUseAtr.class),
                        EnumAdaptor.valueOf(reflectWorkHour, ReflectWorkHourCondition.class)
                )
        );
    }
}
