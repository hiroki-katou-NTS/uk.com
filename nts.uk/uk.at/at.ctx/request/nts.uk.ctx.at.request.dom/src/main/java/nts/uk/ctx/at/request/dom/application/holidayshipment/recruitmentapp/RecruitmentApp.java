package nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.SubTargetDigestion;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 振出申請
 * 
 * @author sonnlb
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentApp extends Application {
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 勤務種類
	 */
	private WorkTypeCode workTypeCD;
	/**
	 * 就業時間帯
	 */
	private WorkTimeCode workTimeCD;

	/**
	 * 勤務時間1
	 */
	private RecruitmentWorkingHour workTime1;
	/**
	 * 勤務時間2
	 */
	private RecruitmentWorkingHour workTime2;

	/**
	 * 消化対象代休管理
	 */
	private List<SubTargetDigestion> subTargetDigestions;

}
