package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetClassification;

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
	
	public void validate() {
		if (execSetting.getPerSchedule().isPerSchedule()) {
			// 対象日は、個人スケジュール作成区分が「する（TRUE）」の場合は必須入力とする。
			if (execSetting.getPerSchedule().getPeriod().getTargetDate() == null) {
				throw new BusinessException("Msg_957");
			}
			// 作成期間は、個人スケジュール作成区分が「する（TRUE）」の場合は必須入力とする。
			if (execSetting.getPerSchedule().getPeriod().getCreationPeriod() == null) {
				throw new BusinessException("Msg_958");
			}
		}
		// 画面項目「B7_20:異動者・新入社員のみ作成」がTRUEの場合、B7_21かB7_22かB7_24のどれかがTRUEでなければならない。
		if (execSetting.getPerSchedule().getTarget().getCreationTarget().value == TargetClassification.CONDITIONS.value) {
			if (!execSetting.getPerSchedule().getTarget().getTargetSetting().isRecreateTransfer() &&
					!execSetting.getPerSchedule().getTarget().getTargetSetting().isCreateEmployee()&&!execSetting.getPerSchedule().getTarget().getTargetSetting().isRecreateWorkType()) {
				throw new BusinessException("Msg_867");
			}
		}
    }
}
