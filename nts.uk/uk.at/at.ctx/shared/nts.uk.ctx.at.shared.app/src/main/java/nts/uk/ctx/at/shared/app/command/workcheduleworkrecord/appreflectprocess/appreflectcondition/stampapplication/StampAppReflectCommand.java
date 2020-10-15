package nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.stampapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.stampapplication.StampAppReflect;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StampAppReflectCommand {
    private int workTimeReflectAtr;
    private int extraWorkTimeReflectAtr;
    private int goOutTimeReflectAtr;
    private int childCareTimeReflecAtr;
    private int supportTimeReflecAtr;
    private int careTimeReflectAtr;
    private int breakTimeReflectAtr;
    
    public StampAppReflect toDomain (String companyId) {
        return StampAppReflect.create(
                companyId,
                workTimeReflectAtr,
                extraWorkTimeReflectAtr,
                goOutTimeReflectAtr,
                childCareTimeReflecAtr,
                supportTimeReflecAtr,
                careTimeReflectAtr,
                breakTimeReflectAtr
        );
    }
}
