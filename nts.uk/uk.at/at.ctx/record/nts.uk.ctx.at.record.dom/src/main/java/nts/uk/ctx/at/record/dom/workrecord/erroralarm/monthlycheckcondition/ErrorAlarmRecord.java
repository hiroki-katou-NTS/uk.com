package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import lombok.AllArgsConstructor;

/**
 * 36協定エラーアラーム
 * @author tutk
 *
 */
@AllArgsConstructor
public enum ErrorAlarmRecord {
	/** エラー */
	ERROR(0,"Enum_ErrorAlarmRecord_ERROR"),
	/** アラーム */
	ALARM(1,"Enum_ErrorAlarmRecord_ALARM");
	
	public final int value;
	
	public final String nameId;
}
