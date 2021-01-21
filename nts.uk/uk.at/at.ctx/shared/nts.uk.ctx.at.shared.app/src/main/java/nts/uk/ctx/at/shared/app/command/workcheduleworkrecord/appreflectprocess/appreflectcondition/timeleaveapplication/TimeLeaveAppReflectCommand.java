package nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveDestination;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeLeaveAppReflectCommand {
    private int reflectActualTimeZone;
    private TimeLeaveDestinationCommand destination;
    private TimeLeaveAppReflectConditionCommand condition;

    public TimeLeaveApplicationReflect toDomain(String companyId) {
        return new TimeLeaveApplicationReflect(
                companyId,
                EnumAdaptor.valueOf(reflectActualTimeZone, NotUseAtr.class),
                new TimeLeaveDestination(
                        EnumAdaptor.valueOf(destination.getFirstBeforeWork(), NotUseAtr.class),
                        EnumAdaptor.valueOf(destination.getSecondBeforeWork(), NotUseAtr.class),
                        EnumAdaptor.valueOf(destination.getPrivateGoingOut(), NotUseAtr.class),
                        EnumAdaptor.valueOf(destination.getUnionGoingOut(), NotUseAtr.class),
                        EnumAdaptor.valueOf(destination.getFirstAfterWork(), NotUseAtr.class),
                        EnumAdaptor.valueOf(destination.getSecondAfterWork(), NotUseAtr.class)
                ),
                new TimeLeaveAppReflectCondition(
                        EnumAdaptor.valueOf(condition.getSuperHoliday60H(), NotUseAtr.class),
                        EnumAdaptor.valueOf(condition.getNursing(), NotUseAtr.class),
                        EnumAdaptor.valueOf(condition.getChildNursing(), NotUseAtr.class),
                        EnumAdaptor.valueOf(condition.getSubstituteLeaveTime(), NotUseAtr.class),
                        EnumAdaptor.valueOf(condition.getAnnualVacationTime(), NotUseAtr.class),
                        EnumAdaptor.valueOf(condition.getSpecialVacationTime(), NotUseAtr.class)

                )
        );
    }
}
