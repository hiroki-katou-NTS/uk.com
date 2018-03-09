package nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.WorkTime;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 振出勤務時間
 * 
 * @author sonnlb
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentWorkingHour {

	/**
	 * 開始時刻
	 */
	private WorkTime startTime;
	/**
	 * 直行
	 */
	private NotUseAtr startUseAtr;
	/**
	 * 終了時刻
	 */
	private WorkTime endTime;
	/**
	 * 直帰
	 */
	private NotUseAtr endUseAtr;

}
