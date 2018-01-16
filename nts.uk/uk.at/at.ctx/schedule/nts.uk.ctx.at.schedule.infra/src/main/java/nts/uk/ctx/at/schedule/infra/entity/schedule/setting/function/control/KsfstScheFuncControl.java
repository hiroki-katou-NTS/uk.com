package nts.uk.ctx.at.schedule.infra.entity.schedule.setting.function.control;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.function.control.DateInWeek;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 勤務予定の機能制御
 * 
 * @author TanLV
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSFST_SCHE_FUNC_CONTROL")
public class KsfstScheFuncControl extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KsfstScheFuncControlPK ksfstScheFuncControlPK;
	
	/** アラームチェック使用区分 */
	@Column(name = "ALARM_CHECK_USE_CLS")
	private int alarmCheckUseCls;
	
	/** 確定使用区分 */
	@Column(name = "CONFIRMED_CLS")
	private int confirmedCls;
	
	/** 公開使用区分 */
	@Column(name = "PUBLIC_CLS")
	private int publicCls;

	/** 出力使用区分 */
	@Column(name = "OUTPUT_CLS")
	private int outputCls;

	/** 勤務希望使用区分 */
	@Column(name = "WORK_DORMITION_CLS")
	private int workDormitionCls;

	/** チーム使用区分 */
	@Column(name = "TEAM_CLS")
	private int teamCls;

	/** ランク使用区分 */
	@Column(name = "RANK_CLS")
	private int rankCls;

	/** 週開始曜日 */
	@Column(name = "START_DATE_IN_WEEK")
	private DateInWeek startDateInWeek;

	/** 略名 */
	@Column(name = "SHORT_NAME_DISP")
	private int shortNameDisp;

	/** 時刻 */
	@Column(name = "TIME_DISP")
	private int timeDisp;

	/** 記号 */
	@Column(name = "SYMBOL_DISP")
	private int symbolDisp;

	/** ２８日 */
	@Column(name = "28_DAYS_CYCLE")
	private int twentyEightDaysCycle;

	/** 末日 */
	@Column(name = "LAST_DAY_DISP")
	private int lastDayDisp;

	/** 個人別使用区分 */
	@Column(name = "INDIVIDUAL_DISP")
	private int individualDisp;

	/** 日付別使用区分 */
	@Column(name = "DISP_BY_DATE")
	private int dispByDate;

	/** シフト別使用区分 */
	@Column(name = "INDICATION_BY_SHIFT")
	private int indicationByShift;

	/** 通常勤務 */
	@Column(name = "REGULAR_WORK")
	private int regularWork;

	/** 流動勤務 */
	@Column(name = "FLUID_WORK")
	private int fluidWork;

	/** フレックス勤務 */
	@Column(name = "WORKING_FOR_FLEX")
	private int workingForFlex;

	/** 残業枠勤務 */
	@Column(name = "OVERTIME_WORK")
	private int overtimeWork;

	/** 通常作成使用区分 */
	@Column(name = "NORMAL_CREATION")
	private int normalCreation;

	/** ｼﾐｭﾚｰｼｮﾝ使用区分 */
	@Column(name = "SIMULATION_CLS")
	private int simulationCls;

	/** 取り込み使用区分 */
	@Column(name = "CAPTURE_USAGE_CLS")
	private int captureUsageCls;

	/** 完了機能使用区分 */
	@Column(name = "COMPLETED_FUNC_CLS")
	private int completedFuncCls;

	/** 完了実行方法 */
	@Column(name = "HOW_TO_COMPLETE")
	private int howToComplete;
	
	/** アラームチェック区分 */
	@Column(name = "ALARM_CHECK_CLS")
	private int alarmCheckCls;

	/** アラーム実行方法 */
	@Column(name = "EXECUTION_METHOD")
	private int executionMethod;

	/** 手修正解除区分 */
	@Column(name = "HANDLE_REPAIR_ATR")
	private int handleRepairAtr;

	/** 確定区分 */
	@Column(name = "CONFIRM")
	private int confirm;

	/** 検索方法 */
	@Column(name = "SEARCH_METHOD")
	private int searchMethod;

	/** 検索方法選択表示区分 */
	@Column(name = "SEARCH_METHOD_DISP_CLS")
	private int searchMethodDispCls;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return ksfstScheFuncControlPK;
	}
}
