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
	EXCECUTION(0, "勤務予定"),
	/**	再実行*/
	RETURN(1, "勤務実績");
	
	public final Integer value;
	
	public final String name;
}
