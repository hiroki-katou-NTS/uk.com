package nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 直行直帰申請
 * @author dudt
 *
 */
@AllArgsConstructor
@Setter
@Getter
public class ApplicationGobackScheInfor {
	/**
	 * 勤務を変更する
	 */
	private ChangeAtrAppGoback changeAtrAppGoback;
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
