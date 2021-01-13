package nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.BreakApplication;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BreakApplicationCommand {

	/**
     * 休憩を反映する
     */
    private int breakReflectAtr;
    
    public BreakApplication toDomain() {
    	return new BreakApplication(EnumAdaptor.valueOf(this.breakReflectAtr, NotUseAtr.class));
    }
}
