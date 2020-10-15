package nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.substituteworkapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.substituteworkapplication.SubstituteWorkAppReflect;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubstituteWorkAppReflectDto {
    private int reflectAttendanceAtr;

    public static SubstituteWorkAppReflectDto fromDomain(SubstituteWorkAppReflect domain) {
        return new SubstituteWorkAppReflectDto(domain.getReflectAttendanceAtr().value);
    }
}
