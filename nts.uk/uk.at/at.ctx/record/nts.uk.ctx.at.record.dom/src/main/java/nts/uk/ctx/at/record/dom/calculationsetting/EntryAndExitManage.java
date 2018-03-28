
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
@Builder
public class EntryAndExitManage {
	private String CID;
	
	// 使用区分
	private boolean notUseAtr; 
}