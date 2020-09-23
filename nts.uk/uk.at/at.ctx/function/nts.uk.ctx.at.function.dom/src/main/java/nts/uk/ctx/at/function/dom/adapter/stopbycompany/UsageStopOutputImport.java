package nts.uk.ctx.at.function.dom.adapter.stopbycompany;

import lombok.Builder;
import lombok.Data;

/**
 * The Class UsageStopOutputImport.
 */
@Data
@Builder
public class UsageStopOutputImport {
	
	/** The is usage stop. 
	 * 利用停止する: true, 利用停止しない: false
	 */
	private boolean isUsageStop;
	
	/** The stop mode. 
	 * 利用停止モード : true
	 */
	private int stopMode;
	
	/** The stop message. 
	 * 利用停止のメッセージ
	 */
	private String stopMessage;
}
