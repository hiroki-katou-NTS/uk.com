package nts.uk.ctx.at.schedule.infra.entity.schedule.setting.functioncontrol;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定の機能制御
 * 
 * @author TanLV
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_SCHE_FUNC_CONTROL")
public class KscstScheFuncControl extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscstScheFuncControlPK kscstScheFuncControlPK;
	
	/** アラームチェック使用区分 */
	@Column(name = "ALARM_CHECK_USE_CLS")
	public int alarmCheckUseCls;
	
	/** 確定使用区分 */
	@Column(name = "CONFIRMED_CLS")
	public int confirmedCls;
	
	/** 公開使用区分 */
	@Column(name = "PUBLIC_CLS")
	public int publicCls;

	/** 出力使用区分 */
	@Column(name = "OUTPUT_CLS")
	public int outputCls;

	/** 勤務希望使用区分 */
	@Column(name = "WORK_DORMITION_CLS")
	public int workDormitionCls;

	/** チーム使用区分 */
	@Column(name = "TEAM_CLS")
	public int teamCls;

	/** ランク使用区分 */
	@Column(name = "RANK_CLS")
	public int rankCls;

	/** 週開始曜日 */
	@Column(name = "START_DATE_IN_WEEK")
	public int startDateInWeek;

	/** 略名 */
	@Column(name = "SHORT_NAME_DISP")
	public int shortNameDisp;

	/** 時刻 */
	@Column(name = "TIME_DISP")
	public int timeDisp;

	/** 記号 */
	@Column(name = "SYMBOL_DISP")
	public int symbolDisp;

	/** ２８日 */
	@Column(name = "DAYS_CYCLE_28")
	public int twentyEightDaysCycle;

	/** 末日 */
	@Column(name = "LAST_DAY_DISP")
	public int lastDayDisp;

	/** 個人別使用区分 */
	@Column(name = "INDIVIDUAL_DISP")
	public int individualDisp;

	/** 日付別使用区分 */
	@Column(name = "DISP_BY_DATE")
	public int dispByDate;

	/** シフト別使用区分 */
	@Column(name = "INDICATION_BY_SHIFT")
	public int indicationByShift;

	/** 通常勤務 */
	@Column(name = "REGULAR_WORK")
	public int regularWork;

	/** 流動勤務 */
	@Column(name = "FLUID_WORK")
	public int fluidWork;

	/** フレックス勤務 */
	@Column(name = "WORKING_FOR_FLEX")
	public int workingForFlex;

	/** 残業枠勤務 */
	@Column(name = "OVERTIME_WORK")
	public int overtimeWork;

	/** 通常作成使用区分 */
	@Column(name = "NORMAL_CREATION")
	public int normalCreation;

	/** ｼﾐｭﾚｰｼｮﾝ使用区分 */
	@Column(name = "SIMULATION_CLS")
	public int simulationCls;

	/** 取り込み使用区分 */
	@Column(name = "CAPTURE_USAGE_CLS")
	public int captureUsageCls;

	/** 完了機能使用区分 */
	@Column(name = "COMPLETED_FUNC_CLS")
	public int completedFuncCls;

	/** 完了実行方法 */
	@Column(name = "HOW_TO_COMPLETE")
	public int howToComplete;
	
	/** アラームチェック区分 */
	@Column(name = "ALARM_CHECK_CLS")
	public int alarmCheckCls;

	/** アラーム実行方法 */
	@Column(name = "EXECUTION_METHOD")
	public int executionMethod;

	/** 手修正解除区分 */
	@Column(name = "HANDLE_REPAIR_ATR")
	public int handleRepairAtr;

	/** 確定区分 */
	@Column(name = "CONFIRM")
	public int confirm;

	/** 検索方法 */
	@Column(name = "SEARCH_METHOD")
	public int searchMethod;

	/** 検索方法選択表示区分 */
	@Column(name = "SEARCH_METHOD_DISP_CLS")
	public int searchMethodDispCls;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="scheFuncControl", orphanRemoval = true)
	public List<KscmtFunctionConpCondition> scheFuncConditions;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstScheFuncControlPK;
	}
}
