/**
 * 10:38:33 AM Jul 21, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums;

import lombok.AllArgsConstructor;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
public enum ErrorAlarmClassification {

	/* エラー */
	ERROR(0, "Enum_ErrorAlarmClassification_Error"),
	/* アラーム */
	ALARM(1, "Enum_ErrorAlarmClassification_Alarm"),
	/* その他 */
	OTHER(2, "Enum_ErrorAlarmClassification_Other");

	public final int value;

	public final String nameId;
	
}
