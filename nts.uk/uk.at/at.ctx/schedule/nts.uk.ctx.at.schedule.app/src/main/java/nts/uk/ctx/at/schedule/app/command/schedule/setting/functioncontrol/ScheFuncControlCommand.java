package nts.uk.ctx.at.schedule.app.command.schedule.setting.functioncontrol;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.ScheFuncCond;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.ScheFuncControl;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tanlv
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheFuncControlCommand {	
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
	
	private List<ScheFuncCondCommand> scheFuncCond;
	
	public ScheFuncControl toDomain() {
		String companyId = AppContexts.user().companyId();
		
		List<ScheFuncCond> scheFuncConds = this.scheFuncCond.stream().map(x-> {
			return new ScheFuncCond(companyId, x.getConditionNo());
		}).collect(Collectors.toList());
		
		return ScheFuncControl.createFromJavaType(companyId, this.alarmCheckUseCls, this.confirmedCls, this.publicCls, this.outputCls, this.workDormitionCls, this.teamCls, 
				this.rankCls, this.startDateInWeek, this.shortNameDisp, this.timeDisp, this.symbolDisp, this.twentyEightDaysCycle, this.lastDayDisp, this.individualDisp, 
				this.dispByDate, this.indicationByShift, this.regularWork, this.fluidWork, this.workingForFlex, this.overtimeWork, this.normalCreation, this.simulationCls, 
				this.captureUsageCls, this.completedFuncCls, this.howToComplete, this.alarmCheckCls, this.executionMethod, this.handleRepairAtr, this.confirm, this.searchMethod, 
				this.searchMethodDispCls, scheFuncConds);
	}
}
