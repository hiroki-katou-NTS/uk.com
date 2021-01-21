package nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork.otworkapply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflect;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtWorkAppReflectCommand {
    private int reflectActualWorkAtr;
    private int reflectWorkInfoAtr;
    private int reflectActualOvertimeHourAtr;
    private int reflectBeforeBreak;
    private int workReflect;
    private int reflectPaytime;
//    private int reflectOptional;
    private int reflectDivergence;
    private int reflectBreakOuting;

    public OtWorkAppReflect toDomain() {
        return OtWorkAppReflect.create(
                reflectActualWorkAtr,
                reflectWorkInfoAtr,
                reflectActualOvertimeHourAtr,
                reflectBeforeBreak,
                workReflect,
                reflectPaytime,
//                reflectOptional,
                reflectDivergence,
                reflectBreakOuting
        );
    }
}
