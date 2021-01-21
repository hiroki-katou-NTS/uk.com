package nts.uk.ctx.at.request.dom.application.workchange.output;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@NoArgsConstructor
@AllArgsConstructor
@Data
/**
 * Refactor4
 * UKDesign.UniversalK.就業.KAF_申請.KAF007_勤務変更申請.A:勤務変更申請（新規）.アルゴリズム.勤務変更申請画面初期（新規）.勤務変更申請の表示情報
 * @author hoangnd
 *
 */
public class AppWorkChangeDispInfo {
	/**
	 * 申請表示情報
	 */
	private AppDispInfoStartupOutput appDispInfoStartupOutput;
	
	/**
	 * 勤務変更申請設定
	 */
	private AppWorkChangeSet appWorkChangeSet;
	
	/**
	 * 勤務種類リスト
	 */
	private List<WorkType> workTypeLst;
	
	/**
	 * 就業時間帯の必須区分
	 */
	private Optional<SetupType> setupType = Optional.empty();
	
	/**
	 * 所定時間設定
	 */
	private Optional<PredetemineTimeSetting> predetemineTimeSetting = Optional.empty();
	
	/**
	 * 選択中の勤務種類
	 */
	private Optional<String> workTypeCD = Optional.empty();
	
	/**
	 * 選択中の就業時間帯
	 */
	private Optional<String> workTimeCD = Optional.empty();
	
//	勤務変更申請の反映
	private ReflectWorkChangeApp reflectWorkChangeApp;
}
