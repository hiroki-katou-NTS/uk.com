package nts.uk.ctx.at.shared.app.command.scherec.appreflectprocess.appreflectcondition.othdwork.hdworkapply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HdWorkAppReflectCommand_Old {
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
