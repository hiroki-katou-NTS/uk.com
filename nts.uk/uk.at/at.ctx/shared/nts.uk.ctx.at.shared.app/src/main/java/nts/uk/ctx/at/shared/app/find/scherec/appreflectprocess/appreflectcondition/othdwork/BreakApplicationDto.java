package nts.uk.ctx.at.shared.app.find.scherec.appreflectprocess.appreflectcondition.othdwork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.BreakApplication;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BreakApplicationDto {

	/**
     * 休憩を反映する
     */
    private int breakReflectAtr;
    
    public static BreakApplicationDto fromDomain(BreakApplication domain) {
    	return new BreakApplicationDto(domain.getBreakReflectAtr().value);
    }
}
