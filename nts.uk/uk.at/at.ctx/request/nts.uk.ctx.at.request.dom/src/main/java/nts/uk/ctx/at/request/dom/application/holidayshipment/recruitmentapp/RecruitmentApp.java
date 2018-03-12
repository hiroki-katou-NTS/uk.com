package nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 振休申請
 * 
 * @author sonnlb
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentApp extends AggregateRoot {
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 勤務種類
	 */
	private String workTypeCD;

	/**
	 * 勤務時間1
	 */
	private RecruitmentWorkingHour workTime1;
	/**
	 * 勤務時間2
	 */
	private RecruitmentWorkingHour workTime2;

}
