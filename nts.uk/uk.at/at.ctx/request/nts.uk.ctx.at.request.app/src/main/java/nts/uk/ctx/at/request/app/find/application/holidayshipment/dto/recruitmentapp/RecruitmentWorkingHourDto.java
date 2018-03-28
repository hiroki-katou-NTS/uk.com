package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.recruitmentapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sonnlb
 * 勤務時間Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RecruitmentWorkingHourDto {
	/**
	 * 開始時刻
	 */
	private int startTime;
	/**
	 * 直行
	 */
	private int startUseAtr;
	/**
	 * 終了時刻
	 */
	private int endTime;
	/**
	 * 直帰
	 */
	private int endUseAtr;

}
