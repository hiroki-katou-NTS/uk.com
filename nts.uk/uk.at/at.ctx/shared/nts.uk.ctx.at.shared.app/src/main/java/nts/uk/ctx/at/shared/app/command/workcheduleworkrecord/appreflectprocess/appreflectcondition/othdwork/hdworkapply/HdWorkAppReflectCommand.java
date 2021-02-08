package nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork.hdworkapply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HdWorkAppReflectCommand {
	/**
     * 事前
     */
    private BeforeHdWorkAppReflectCommand before;
    
    /**
     * 事後
     */
    private AfterHdWorkAppReflectCommand after;
    
    public HdWorkAppReflect toDomain() {
        return new HdWorkAppReflect(
        		this.before.toDomain(),
        		this.after.toDomain()
        		);
    }
}
