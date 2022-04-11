package nts.uk.ctx.at.shared.app.command.scherec.appreflectprocess.appreflectcondition.othdwork.otworkapply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflect;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtWorkAppReflectCommand {
    private int reflectActualWorkAtr;
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
