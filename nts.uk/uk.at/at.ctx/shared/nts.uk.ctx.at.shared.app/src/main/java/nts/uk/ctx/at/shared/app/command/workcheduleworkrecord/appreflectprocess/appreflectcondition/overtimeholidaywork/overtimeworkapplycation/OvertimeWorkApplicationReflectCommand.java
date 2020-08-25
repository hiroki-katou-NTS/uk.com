package nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.overtimeworkapplycation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.overtimeworkapplycation.OvertimeWorkApplicationReflect;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OvertimeWorkApplicationReflectCommand {
    private int reflectActualWorkAtr;
    private int reflectWorkInfoAtr;
    private int reflectActualOvertimeHourAtr;
    private int reflectBeforeBreak;
    private int workReflect;
    private int reflectPaytime;
    private int reflectOptional;
    private int reflectDivergence;
    private int reflectBreakOuting;

    public OvertimeWorkApplicationReflect toDomain() {
        return OvertimeWorkApplicationReflect.create(
                reflectActualWorkAtr,
                reflectWorkInfoAtr,
                reflectActualOvertimeHourAtr,
                reflectBeforeBreak,
                workReflect,
                reflectPaytime,
                reflectOptional,
                reflectDivergence,
                reflectBreakOuting
        );
    }
}
