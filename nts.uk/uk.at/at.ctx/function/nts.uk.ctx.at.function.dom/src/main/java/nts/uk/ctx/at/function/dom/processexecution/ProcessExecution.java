package nts.uk.ctx.at.function.dom.processexecution;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetClassification;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

/**
 * 更新処理自動実行
 */
@Getter
@AllArgsConstructor
public class ProcessExecution extends AggregateRoot {
	/* 会社ID */
	private String companyId;
	
	/* コード */
	private ExecutionCode execItemCd;
	
	/* 名称 */
	private ExecutionName execItemName;
	
	/* 実行範囲 */
	private ProcessExecutionScope execScope;
	
	/* 実行設定 */
	private ProcessExecutionSetting execSetting;
	
	/* 実行種別*/
	private ProcessExecType processExecType;
	
	public void validateVer2() {
//		List<String> listError = new ArrayList<>();
		if (execSetting.getPerSchedule().isPerSchedule()) {
			// 対象日は、個人スケジュール作成区分（B7_1）が「する（TRUE）」の場合は必須入力とする。
			if (execSetting.getPerSchedule().getPeriod().getTargetDate() == null) {
				throw new BusinessException("Msg_957");
			}
			// 作成期間は、個人スケジュール作成区分が「する（TRUE）」の場合は必須入力とする。
			if (execSetting.getPerSchedule().getPeriod().getCreationPeriod() == null) {
				throw new BusinessException("Msg_958");
			}
		}
		//B16_2がTRUEの場合
		if(processExecType == ProcessExecType.NORMAL_EXECUTION) {
			//実行設定(B7_1,B8_1,B9_1,B10_1,B11_1)のチェックボックスのうち1つ以上TUREになっていなければならない
			//TODO:B11_1 chua lam
			if(!execSetting.getPerSchedule().isPerSchedule() && //B7_1
			   !execSetting.getDailyPerf().isDailyPerfCls() && //B8_1
			   !execSetting.isReflectResultCls() && //B9_1
			   !execSetting.isMonthlyAggCls()  && //B10_1
			   execSetting.getAppRouteUpdateDaily().getAppRouteUpdateAtr() == NotUseAtr.NOT_USE && //B12_1
			   execSetting.getAppRouteUpdateMonthly() == NotUseAtr.NOT_USE) { //B12_3 
				
				throw new BusinessException("Msg_1230");
			}
		}else {//B16_3がTRUEの場合
			//実行設定(B14_2,B14_3,B14_4)のチェックボックスのうち1つ以上TUREになっていなければならない。
			if(!execSetting.getDailyPerf().isDailyPerfCls() &&//B14_3
			   !execSetting.getPerSchedule().isPerSchedule() &&//B14_2
			   execSetting.getAppRouteUpdateDaily().getAppRouteUpdateAtr() == NotUseAtr.NOT_USE) {//B14_4
				throw new BusinessException("Msg_1230");
			}
			

			//実行設定(B15_2,B15_3)のチェックボックスのうち1つ以上TUREになっていなければならない。
			if(!execSetting.getDailyPerf().getTargetGroupClassification().isRecreateTransfer()&&//B15_2
			   !execSetting.getPerSchedule().getTarget().getTargetSetting().isRecreateWorkType()) {//B15_3
				throw new BusinessException("Msg_1230");
			}
		}
//		return listError;
	}
	
//	public void validate() {
//		if (execSetting.getPerSchedule().isPerSchedule()) {
//			// 対象日は、個人スケジュール作成区分が「する（TRUE）」の場合は必須入力とする。
//			if (execSetting.getPerSchedule().getPeriod().getTargetDate() == null) {
//				throw new BusinessException("Msg_957");
//			}
//			// 作成期間は、個人スケジュール作成区分が「する（TRUE）」の場合は必須入力とする。
//			if (execSetting.getPerSchedule().getPeriod().getCreationPeriod() == null) {
//				throw new BusinessException("Msg_958");
//			}
//		}
//		// 画面項目「B7_20:異動者・新入社員のみ作成」がTRUEの場合、B7_21かB7_22かB7_24のどれかがTRUEでなければならない。
//		if (execSetting.getPerSchedule().getTarget().getCreationTarget().value == TargetClassification.CONDITIONS.value) {
//			if (!execSetting.getPerSchedule().getTarget().getTargetSetting().isRecreateTransfer() &&
//					!execSetting.getPerSchedule().getTarget().getTargetSetting().isCreateEmployee()&&!execSetting.getPerSchedule().getTarget().getTargetSetting().isRecreateWorkType()) {
//				throw new BusinessException("Msg_867");
//			}
//		}
//		
//		//実行設定.個人スケジュール作成.個人スケジュール作成区分		B7_1						
//		boolean perSchedule = execSetting.getPerSchedule().isPerSchedule();
//
//		//実行設定.日別実績の作成・計算.日別実績の作成・計算区分  B8_1
//		boolean dailyPerfCls = execSetting.getDailyPerf().isDailyPerfCls();
//
//		//実行設定.承認結果反映 B9_1
//		boolean reflectResultCls = execSetting.isReflectResultCls();
//
//		//実行設定.月別集計	B10_1							
//		boolean monthlyAggCls = execSetting.isMonthlyAggCls();
//									
//		//実行設定.アラーム抽出（個人別）.アラーム抽出（個人別）区分 B11_1
//		boolean indvAlarmCls = execSetting.getIndvAlarm().isIndvAlarmCls();
//
//		//実行設定.アラーム抽出（職場別）.アラーム抽出（職場別）区分 B12_1								
//		boolean wkpAlarmCls = execSetting.getWkpAlarm().isWkpAlarmCls();
//		if(!perSchedule && !dailyPerfCls && !reflectResultCls && !monthlyAggCls && !indvAlarmCls && !wkpAlarmCls){
//			throw new BusinessException("Msg_1230");
//		}
//    }
}
