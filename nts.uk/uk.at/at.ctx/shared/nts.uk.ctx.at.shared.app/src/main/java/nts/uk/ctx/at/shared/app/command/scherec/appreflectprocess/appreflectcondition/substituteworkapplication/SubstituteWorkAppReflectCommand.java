package nts.uk.ctx.at.shared.app.command.scherec.appreflectprocess.appreflectcondition.substituteworkapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.substituteworkapplication.SubstituteWorkAppReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubstituteWorkAppReflectCommand {
    private int reflectAttendanceAtr;

    public SubstituteWorkAppReflect toDomain() {
        return new SubstituteWorkAppReflect(EnumAdaptor.valueOf(reflectAttendanceAtr, NotUseAtr.class));
    }
}
