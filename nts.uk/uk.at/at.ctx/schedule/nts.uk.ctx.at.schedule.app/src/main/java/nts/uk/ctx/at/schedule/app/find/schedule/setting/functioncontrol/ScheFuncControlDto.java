package nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.ScheFuncControl;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScheFuncControlDto {
	/** 会社ID */
	private String companyId;
	
	/** アラームチェック使用区分 */
	private int alarmCheckUseCls;
	
	/** 確定使用区分 */
	private int confirmedCls;
	
	/** 公開使用区分 */
	private int publicCls;

	/** 出力使用区分 */
	private int outputCls;

	/** 勤務希望使用区分 */
	private int workDormitionCls;

	/** チーム使用区分 */
	private int teamCls;

	/** ランク使用区分 */
	private int rankCls;

	/** 週開始曜日 */
	private int startDateInWeek;

	/** 略名 */
	private int shortNameDisp;

	/** 時刻 */
	private int timeDisp;

	/** 記号 */
	private int symbolDisp;

	/** ２８日 */
	private int twentyEightDaysCycle;

	/** 末日 */
	private int lastDayDisp;

	/** 個人別使用区分 */
	private int individualDisp;

	/** 日付別使用区分 */
	private int dispByDate;

	/** シフト別使用区分 */
	private int indicationByShift;

	/** 通常勤務 */
	private int regularWork;

	/** 流動勤務 */
	private int fluidWork;

	/** フレックス勤務 */
	private int workingForFlex;

	/** 残業枠勤務 */
	private int overtimeWork;

	/** 通常作成使用区分 */
	private int normalCreation;

	/** ｼﾐｭﾚｰｼｮﾝ使用区分 */
	private int simulationCls;

	/** 取り込み使用区分 */
	private int captureUsageCls;

	/** 完了機能使用区分 */
	private int completedFuncCls;

	/** 完了実行方法 */
	private int howToComplete;
	
	/** アラームチェック区分 */
	private int alarmCheckCls;

	/** アラーム実行方法 */
	private int executionMethod;

	/** 手修正解除区分 */
	private int handleRepairAtr;

	/** 確定区分 */
	private int confirm;

	/** 検索方法 */
	private int searchMethod;

	/** 検索方法選択表示区分 */
	private int searchMethodDispCls;
	
	private List<ScheFuncCondDto> scheFuncCond;
	
	/**
	 * From domain
	 * 
	 * @param domain
	 * @return
	 */
	public static ScheFuncControlDto fromDomain(ScheFuncControl domain){
    	List<ScheFuncCondDto> items = domain.getScheFuncCond().stream()
				.map(x-> ScheFuncCondDto.fromDomain(x))
				.collect(Collectors.toList());
    	
		return new ScheFuncControlDto(
				domain.getCompanyId(),
				domain.getAlarmCheckUseCls().value,
				domain.getConfirmedCls().value,
				domain.getPublicCls().value,
				domain.getOutputCls().value,
				domain.getWorkDormitionCls().value,
				domain.getTeamCls().value,
				domain.getRankCls().value,
				domain.getStartDateInWeek().value,
				domain.getShortNameDisp().value,
				domain.getTimeDisp().value,
				domain.getSymbolDisp().value,
				domain.getTwentyEightDaysCycle().value,
				domain.getLastDayDisp().value,
				domain.getIndividualDisp().value,
				domain.getDispByDate().value,
				domain.getIndicationByShift().value,
				domain.getRegularWork().value,
				domain.getFluidWork().value,
				domain.getWorkingForFlex().value,
				domain.getOvertimeWork().value,
				domain.getNormalCreation().value,
				domain.getSimulationCls().value,
				domain.getCaptureUsageCls().value,
				domain.getCompletedFuncCls().value,
				domain.getHowToComplete().value,
				domain.getAlarmCheckCls().value,
				domain.getExecutionMethod().value,
				domain.getHandleRepairAtr().value,
				domain.getConfirm().value,
				domain.getSearchMethod().value,
				domain.getSearchMethodDispCls().value,
				items
		);
	}
}
