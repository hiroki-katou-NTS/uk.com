package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatus;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 休日出勤申請起動時の表示情報(申請対象日関係あり)
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@Getter
@Setter
public class HdWorkDispInfoWithDateOutput_Old {
	
	/**
	 * 代休管理区分
	 */
	private boolean subHdManage;
	
	/**
	 * 休出申請指示
	 */
	private HolidayWorkInstruction appHdWorkInstruction;
	
	/**
	 * 初期選択勤務種類
	 */
	private String workTypeCD;
	
	/**
	 * 初期選択就業時間帯
	 */
	private String workTimeCD;
	
	/**
	 * 開始時刻
	 */
	private Integer startTime;
	
	/**
	 * 終了時刻
	 */
	private Integer endTime;
	
	/**
	 * 表示する実績内容
	 */
	private List<AchievementOutput> achievementOutputLst;
	
	/**
	 * 実績状態
	 */
	private ActualStatus actualStatus;
	
	/**
	 * 勤怠時間の超過状態
	 */
	private String overtimeStatus;
	
	/**
	 * 月別実績の36協定時間状態
	 */
	private AgreementTimeStatusOfMonthly agreementTimeStatusOfMonthly;
	
	/**
	 * 初期選択勤務種類名称
	 */
	private String workTypeName;
	
	/**
	 * 初期選択就業時間帯名称
	 */
	private String workTimeName;
	
	/**
	 * 勤務種類リスト
	 */
	private Optional<List<WorkType>> workTypeLst;
	
	/**
	 * 休憩時間帯設定リスト
	 */
	private Optional<List<DeductionTime>> deductionTimeLst;
	
}
