package nts.uk.ctx.at.schedule.pub.appreflectprocess;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ApplicationGobackScheInforDto {
	/**
	 * 勤務を変更する
	 */
	private ChangeAtrAppGobackPub changeAtrAppGoback;
	/**	勤務種類 */
	private String workType;
	/**	就業時間帯  */
	private String workTime;
	/** 勤務時間開始1 */
	private Integer workTimeStart1;
	/**	勤務時間終了1 */
	private Integer workTimeEnd1;
	/** 勤務時間開始2 */
	private Integer workTimeStart2;
	/**	勤務時間終了2 */
	private Integer workTimeEnd2;
}
