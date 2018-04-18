
package nts.uk.ctx.at.record.dom.calculationsetting;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class EntryAndExitManage.
 *
 * @author hoangdd
 */
@Getter
@Setter

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder
public class EntryAndExitManage {
	// /*会社ID*/
	private String CID;
	
	// 使用区分
	private boolean notUseAtr; 
}