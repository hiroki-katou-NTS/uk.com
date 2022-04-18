package nts.uk.ctx.at.shared.app.command.scherec.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.ReflectWorkHourCondition;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp.SubstituteLeaveAppReflect;
import nts.uk.shr.com.context.AppContexts;
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
        		AppContexts.user().companyId(),
                new VacationAppReflectOption(
                        EnumAdaptor.valueOf(oneDayLeaveDeleteAttendance, NotUseAtr.class),
                        EnumAdaptor.valueOf(reflectWorkHour, ReflectWorkHourCondition.class)
                )
        );
    }
}
