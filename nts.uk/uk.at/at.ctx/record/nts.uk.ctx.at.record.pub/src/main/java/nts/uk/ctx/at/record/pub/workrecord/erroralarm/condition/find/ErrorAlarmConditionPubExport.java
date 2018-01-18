package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorAlarmConditionPubExport {

	/* ID */
	private String errorAlarmCheckID;
	/* チェック条件*/
	private AlarmCheckTargetConditionPubExport alarmCheckTargetCondition;
	/* 勤務種類の条件 */
	private WorkTypeConditionPubExport workTypeCondition;
	/* 勤怠項目の条件 */
	private AttendanceItemConditionPubExport attendanceItemCondition;
	/* 就業時間帯の条件 */
	private WorkTimeConditionPubExport workTimeCondition;
	/* 表示メッセージ */
	private String displayMessage;

	/* 連続期間 */
	private int continuousPeriod;
	
	public ErrorAlarmConditionPubExport(String errorAlarmCheckID,
			AlarmCheckTargetConditionPubExport alarmCheckTargetCondition,
			WorkTypeConditionPubExport workTypeCondition, 
			AttendanceItemConditionPubExport attendanceItemCondition,
			WorkTimeConditionPubExport workTimeCondition,
			String displayMessage,
			int continuousPeriod
			) {
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.alarmCheckTargetCondition = alarmCheckTargetCondition;
		this.workTypeCondition = workTypeCondition;
		this.attendanceItemCondition = attendanceItemCondition;
		this.workTimeCondition = workTimeCondition;
		this.displayMessage = displayMessage;
		this.continuousPeriod = continuousPeriod;
	}
	
}
