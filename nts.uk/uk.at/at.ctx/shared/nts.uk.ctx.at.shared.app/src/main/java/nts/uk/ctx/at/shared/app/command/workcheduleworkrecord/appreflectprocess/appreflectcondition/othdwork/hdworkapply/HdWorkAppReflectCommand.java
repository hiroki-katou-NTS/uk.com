package nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork.hdworkapply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HdWorkAppReflectCommand {
    private int reflectActualHolidayWorkAtr;
    private int workReflect;
    private int reflectPaytime;
//    private int reflectOptional;
    private int reflectDivergence;
    private int reflectBreakOuting;

    public HdWorkAppReflect toDomain() {
        return HdWorkAppReflect.create(
                reflectActualHolidayWorkAtr,
                workReflect,
                reflectPaytime,
//                reflectOptional,
                reflectDivergence,
                reflectBreakOuting
        );
    }
}
