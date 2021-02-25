package nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveDestination;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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

    public static TimeLeaveApplicationReflect toDomain(TimeLeaveAppReflectDto dto) {
        return new TimeLeaveApplicationReflect(
            AppContexts.user().companyId(),
            EnumAdaptor.valueOf(dto.reflectActualTimeZone, NotUseAtr.class),
            new TimeLeaveDestination(
                EnumAdaptor.valueOf(dto.destination.getFirstBeforeWork(), NotUseAtr.class),
                EnumAdaptor.valueOf(dto.destination.getSecondBeforeWork(), NotUseAtr.class),
                EnumAdaptor.valueOf(dto.destination.getPrivateGoingOut(), NotUseAtr.class),
                EnumAdaptor.valueOf(dto.destination.getUnionGoingOut(), NotUseAtr.class),
                EnumAdaptor.valueOf(dto.destination.getFirstAfterWork(), NotUseAtr.class),
                EnumAdaptor.valueOf(dto.destination.getSecondAfterWork(), NotUseAtr.class)
            ),
            new TimeLeaveAppReflectCondition(
                EnumAdaptor.valueOf(dto.condition.getSuperHoliday60H(), NotUseAtr.class),
                EnumAdaptor.valueOf(dto.condition.getNursing(), NotUseAtr.class),
                EnumAdaptor.valueOf(dto.condition.getChildNursing(), NotUseAtr.class),
                EnumAdaptor.valueOf(dto.condition.getSubstituteLeaveTime(), NotUseAtr.class),
                EnumAdaptor.valueOf(dto.condition.getAnnualVacationTime(), NotUseAtr.class),
                EnumAdaptor.valueOf(dto.condition.getSpecialVacationTime(), NotUseAtr.class)
            )
        );
    }

}
