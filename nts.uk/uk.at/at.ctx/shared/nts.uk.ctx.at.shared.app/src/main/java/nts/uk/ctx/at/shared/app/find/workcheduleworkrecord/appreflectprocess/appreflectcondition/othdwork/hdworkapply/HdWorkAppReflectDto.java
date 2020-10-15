package nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork.hdworkapply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HdWorkAppReflectDto {
    private int reflectActualHolidayWorkAtr;
    private int workReflect;
    private int reflectPaytime;
//    private int reflectOptional;
    private int reflectDivergence;
    private int reflectBreakOuting;

    public static HdWorkAppReflectDto fromDomain(HdWorkAppReflect domain) {
        return new HdWorkAppReflectDto(
                domain.getBefore().getReflectActualHolidayWorkAtr().value,
                domain.getAfter().getWorkReflect().value,
                domain.getAfter().getOthersReflect().getReflectPaytimeAtr().value,
//                domain.getAfter().getOthersReflect().getReflectOptionalItemsAtr().value,
                domain.getAfter().getOthersReflect().getReflectDivergentReasonAtr().value,
                domain.getAfter().getBreakLeaveApplication().getBreakReflectAtr().value
        );
    }
}
