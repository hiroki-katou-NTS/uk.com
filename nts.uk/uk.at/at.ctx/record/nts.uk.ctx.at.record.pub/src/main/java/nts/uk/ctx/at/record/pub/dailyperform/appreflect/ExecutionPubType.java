package nts.uk.ctx.at.record.pub.dailyperform.appreflect;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExecutionPubType {
	/**通常実行*/
	EXCECUTION(0, "勤務予定"),
	/**	再実行*/
	RETURN(1, "勤務実績");
	
	public final Integer value;
	
	public final String name;
}
