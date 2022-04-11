package nts.uk.ctx.at.shared.app.find.scherec.appreflectprocess.appreflectcondition.othdwork.otworkapply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflect;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtWorkAppReflectDto {
    private int reflectActualWorkAtr;
    private int reflectActualOvertimeHourAtr;
    private int reflectBeforeBreak;
    private int workReflect;
    private int reflectPaytime;
//    private int reflectOptional;
    private int reflectDivergence;
    private int reflectBreakOuting;

    public static OtWorkAppReflectDto fromDomain(OtWorkAppReflect domain) {
        return new OtWorkAppReflectDto(
                domain.getReflectActualWorkAtr().value,
                domain.getBefore().getReflectActualOvertimeHourAtr().value,
                domain.getBefore().getBreakLeaveApplication().getBreakReflectAtr().value,
                domain.getAfter().getWorkReflect().value,
                domain.getAfter().getOthersReflect().getReflectPaytimeAtr().value,
//                domain.getAfter().getOthersReflect().getReflectOptionalItemsAtr().value,
                domain.getAfter().getOthersReflect().getReflectDivergentReasonAtr().value,
                domain.getAfter().getBreakLeaveApplication().getBreakReflectAtr().value
        );
    }
}
