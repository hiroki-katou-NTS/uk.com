package nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class ErrorAlarmConAdapterDto {
	/* ID */
	private String errorAlarmCheckID;
	/* チェック条件*/
	private AlarmCheckTargetConAdapterDto alCheckTargetCondition;
	/* 勤務種類の条件 */
	private WorkTypeConAdapterDto workTypeCondition;
	/* 勤怠項目の条件 */
	private AttendanceItemConAdapterDto atdItemCondition;
	/* 就業時間帯の条件 */
	private WorkTimeConAdapterDto workTimeCondition;
	/* 表示メッセージ */
	private String displayMessage;

	/* 連続期間 */
	private int continuousPeriod;

	public ErrorAlarmConAdapterDto(String errorAlarmCheckID, AlarmCheckTargetConAdapterDto alarmCheckTargetCondition,
			WorkTypeConAdapterDto workTypeCondition, AttendanceItemConAdapterDto attendanceItemCondition,
			WorkTimeConAdapterDto workTimeCondition, String displayMessage, int continuousPeriod) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.alCheckTargetCondition = alarmCheckTargetCondition;
		this.workTypeCondition = workTypeCondition;
		this.atdItemCondition = attendanceItemCondition;
		this.workTimeCondition = workTimeCondition;
		this.displayMessage = displayMessage;
		this.continuousPeriod = continuousPeriod;
	}
	
}
