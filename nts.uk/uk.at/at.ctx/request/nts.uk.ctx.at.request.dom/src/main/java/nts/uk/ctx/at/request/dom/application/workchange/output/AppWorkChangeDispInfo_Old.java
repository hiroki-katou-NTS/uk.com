package nts.uk.ctx.at.request.dom.application.workchange.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSet_Old;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 勤務変更申請の表示情報
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppWorkChangeDispInfo_Old {
	
	/**
	 * 申請表示情報
	 */
	private AppDispInfoStartupOutput appDispInfoStartupOutput;
	
	/**
	 * 勤務変更申請設定
	 */
	private AppWorkChangeSet_Old appWorkChangeSet;
	
	/**
	 * 勤務種類リスト
	 */
	private List<WorkType> workTypeLst;
	
	/**
	 * 就業時間帯の必須区分
	 */
	private SetupType setupType;
	
	/**
	 * 所定時間設定
	 */
	private PredetemineTimeSetting predetemineTimeSetting;
	
	/**
	 * 選択中の勤務種類
	 */
	private String workTypeCD;
	
	/**
	 * 選択中の就業時間帯
	 */
	private String workTimeCD;
	
}
