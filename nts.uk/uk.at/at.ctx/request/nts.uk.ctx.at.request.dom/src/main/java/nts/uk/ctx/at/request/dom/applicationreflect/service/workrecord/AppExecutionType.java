package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord;

import lombok.AllArgsConstructor;
/**
 * 実行種別
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum AppExecutionType {
	/**通常実行*/
	EXCECUTION(0, "通常実行"),
	/**	再実行*/
	RETURN(1, "再実行");
	
	public final Integer value;
	
	public final String name;
}
