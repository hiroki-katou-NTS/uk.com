package nts.uk.ctx.at.shared.app.find.scherec.appreflectprocess.appreflectcondition.substituteworkapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.substituteworkapplication.SubstituteWorkAppReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubstituteWorkAppReflectDto {
    private int reflectAttendanceAtr;

    public static SubstituteWorkAppReflectDto fromDomain(SubstituteWorkAppReflect domain) {
        return new SubstituteWorkAppReflectDto(domain.getReflectAttendanceAtr().value);
    }
    
    public SubstituteWorkAppReflect toDomain() {
        return new SubstituteWorkAppReflect(EnumAdaptor.valueOf(reflectAttendanceAtr, NotUseAtr.class));
    }
}
