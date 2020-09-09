package nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeLeaveAppReflectDto {
    private int reflectActualTimeZone;
    private TimeLeaveDestinationDto destination;
    private TimeLeaveAppReflectConditionDto condition;

    public static TimeLeaveAppReflectDto fromDomain(TimeLeaveApplicationReflect domain) {
        return new TimeLeaveAppReflectDto(
                domain.getReflectActualTimeZone().value,
                new TimeLeaveDestinationDto(
                        domain.getDestination().getFirstBeforeWork().value,
                        domain.getDestination().getSecondBeforeWork().value,
                        domain.getDestination().getFirstAfterWork().value,
                        domain.getDestination().getSecondAfterWork().value,
                        domain.getDestination().getPrivateGoingOut().value,
                        domain.getDestination().getUnionGoingOut().value
                ),
                new TimeLeaveAppReflectConditionDto(
                        domain.getCondition().getSuperHoliday60H().value,
                        domain.getCondition().getNursing().value,
                        domain.getCondition().getChildNursing().value,
                        domain.getCondition().getSubstituteLeaveTime().value,
                        domain.getCondition().getAnnualVacationTime().value,
                        domain.getCondition().getSpecialVacationTime().value
                )
        );
    }
}
