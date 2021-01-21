package nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.stampapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.stampapplication.StampAppReflect;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StampAppReflectDto {
    private int workTimeReflectAtr;
    private int extraWorkTimeReflectAtr;
    private int goOutTimeReflectAtr;
    private int childCareTimeReflecAtr;
    private int supportTimeReflecAtr;
    private int careTimeReflectAtr;
    private int breakTimeReflectAtr;
    
    public static StampAppReflectDto fromDomain(StampAppReflect domain) {
        return new StampAppReflectDto(
                domain.getWorkReflectAtr().value,
                domain.getExtraWorkReflectAtr().value,
                domain.getGoOutReflectAtr().value,
                domain.getChildCareReflectAtr().value,
                domain.getSupportReflectAtr().value,
                domain.getCareReflectAtr().value,
                domain.getBreakReflectAtr().value
        );
    }
}
