package nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 勤務予定の機能制御
 * 
 * @author TanLV
 *
 */
@Getter
@AllArgsConstructor
public class ScheFuncControl extends AggregateRoot {
	/** 会社ID */
	private String companyId;
	
	/** アラームチェック使用区分 */
	private UseAtr alarmCheckUseCls;
	
	/** 確定使用区分 */
	private UseAtr confirmedCls;
	
	/** 公開使用区分 */
	private UseAtr publicCls;

	/** 出力使用区分 */
	private UseAtr outputCls;

	/** 勤務希望使用区分 */
	private UseAtr workDormitionCls;

	/** チーム使用区分 */
	private UseAtr teamCls;

	/** ランク使用区分 */
	private UseAtr rankCls;

	/** 週開始曜日 */
	private DateInWeek startDateInWeek;

	/** 略名 */
	private UseAtr shortNameDisp;

	/** 時刻 */
	private UseAtr timeDisp;

	/** 記号 */
	private UseAtr symbolDisp;

	/** ２８日 */
	private UseAtr twentyEightDaysCycle;

	/** 末日 */
	private UseAtr lastDayDisp;

	/** 個人別使用区分 */
	private UseAtr individualDisp;

	/** 日付別使用区分 */
	private UseAtr dispByDate;

	/** シフト別使用区分 */
	private UseAtr indicationByShift;

	/** 通常勤務 */
	private UseAtr regularWork;

	/** 流動勤務 */
	private UseAtr fluidWork;

	/** フレックス勤務 */
	private UseAtr workingForFlex;

	/** 残業枠勤務 */
	private UseAtr overtimeWork;

	/** 通常作成使用区分 */
	private UseAtr normalCreation;

	/** ｼﾐｭﾚｰｼｮﾝ使用区分 */
	private UseAtr simulationCls;

	/** 取り込み使用区分 */
	private UseAtr captureUsageCls;

	/** 完了機能使用区分 */
	private UseAtr completedFuncCls;

	/** 完了実行方法 */
	private ExecutionMethod howToComplete;
	
	/** アラームチェック区分 */
	private UseAtr alarmCheckCls;

	/** アラーム実行方法 */
	private ExecutionMethod executionMethod;

	/** 手修正解除区分 */
	private UseAtr handleRepairAtr;

	/** 確定区分 */
	private UseAtr confirm;

	/** 検索方法 */
	private UseAtr searchMethod;

	/** 検索方法選択表示区分 */
	private SearchMethod searchMethodDispCls;
	
	private List<ScheFuncCond> scheFuncCond;

	/**
	 * Create From Java Type
	 * 
	 * @param companyId
	 * @param alarmCheckUseCls
	 * @param confirmedCls
	 * @param publicCls
	 * @param outputCls
	 * @param workDormitionCls
	 * @param teamCls
	 * @param rankCls
	 * @param startDateInWeek
	 * @param shortNameDisp
	 * @param timeDisp
	 * @param symbolDisp
	 * @param twentyEightDaysCycle
	 * @param lastDayDisp
	 * @param individualDisp
	 * @param dispByDate
	 * @param indicationByShift
	 * @param regularWork
	 * @param fluidWork
	 * @param workingForFlex
	 * @param overtimeWork
	 * @param normalCreation
	 * @param simulationCls
	 * @param captureUsageCls
	 * @param completedFuncCls
	 * @param howToComplete
	 * @param alarmCheckCls
	 * @param executionMethod
	 * @param handleRepairAtr
	 * @param confirm
	 * @param searchMethod
	 * @param searchMethodDispCls
	 * @param scheFuncCond
	 * @return
	 */
	public static ScheFuncControl createFromJavaType(String companyId, int alarmCheckUseCls, int confirmedCls, int publicCls,
			int outputCls, int workDormitionCls, int teamCls, int rankCls, int startDateInWeek,
			int shortNameDisp, int timeDisp, int symbolDisp, int twentyEightDaysCycle, int lastDayDisp,
			int individualDisp, int dispByDate, int indicationByShift, int regularWork, int fluidWork,
			int workingForFlex, int overtimeWork, int normalCreation, int simulationCls,
			int captureUsageCls, int completedFuncCls, int howToComplete, int alarmCheckCls,
			int executionMethod, int handleRepairAtr, int confirm, int searchMethod,
			int searchMethodDispCls, List<ScheFuncCond> scheFuncCond) {
		
		return new ScheFuncControl(companyId, EnumAdaptor.valueOf(alarmCheckUseCls, UseAtr.class), EnumAdaptor.valueOf(confirmedCls, UseAtr.class), EnumAdaptor.valueOf(publicCls, UseAtr.class),
				EnumAdaptor.valueOf(outputCls, UseAtr.class), EnumAdaptor.valueOf(workDormitionCls, UseAtr.class), EnumAdaptor.valueOf(teamCls, UseAtr.class), 
				EnumAdaptor.valueOf(rankCls, UseAtr.class), EnumAdaptor.valueOf(startDateInWeek, DateInWeek.class), EnumAdaptor.valueOf(shortNameDisp, UseAtr.class), 
				EnumAdaptor.valueOf(timeDisp, UseAtr.class), EnumAdaptor.valueOf(symbolDisp, UseAtr.class), EnumAdaptor.valueOf(twentyEightDaysCycle, UseAtr.class), 
				EnumAdaptor.valueOf(lastDayDisp, UseAtr.class), EnumAdaptor.valueOf(individualDisp, UseAtr.class), EnumAdaptor.valueOf(dispByDate, UseAtr.class), 
				EnumAdaptor.valueOf(indicationByShift, UseAtr.class), EnumAdaptor.valueOf(regularWork, UseAtr.class), EnumAdaptor.valueOf(fluidWork, UseAtr.class),
				EnumAdaptor.valueOf(workingForFlex, UseAtr.class), EnumAdaptor.valueOf(overtimeWork, UseAtr.class), EnumAdaptor.valueOf(normalCreation, UseAtr.class), 
				EnumAdaptor.valueOf(simulationCls, UseAtr.class), EnumAdaptor.valueOf(captureUsageCls, UseAtr.class), EnumAdaptor.valueOf(completedFuncCls, UseAtr.class), 
				EnumAdaptor.valueOf(howToComplete, ExecutionMethod.class), EnumAdaptor.valueOf(alarmCheckCls, UseAtr.class), EnumAdaptor.valueOf(executionMethod, ExecutionMethod.class), 
				EnumAdaptor.valueOf(handleRepairAtr, UseAtr.class), EnumAdaptor.valueOf(confirm, UseAtr.class), EnumAdaptor.valueOf(searchMethod, UseAtr.class),
				EnumAdaptor.valueOf(searchMethodDispCls, SearchMethod.class), scheFuncCond);
	}
}
