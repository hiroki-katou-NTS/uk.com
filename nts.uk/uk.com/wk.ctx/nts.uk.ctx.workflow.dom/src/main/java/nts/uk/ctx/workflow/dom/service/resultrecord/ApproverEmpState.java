package nts.uk.ctx.workflow.dom.service.resultrecord;

import lombok.AllArgsConstructor;

/**
 * 基準社員のルートの状況
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum ApproverEmpState {
	/**
	 * 完了
	 */
	COMPLETE(0, "完了"),
	/**
	 * フェーズ通過済み
	 */
	PHASE_PASS(1, "フェーズ通過済み"),
	/**
	 * フェーズ最中
	 */
	PHASE_DURING(2, "フェーズ最中"),
	/**
	 * フェーズ未到達
	 */
	PHASE_LESS(3, "フェーズ未到達");
	
	public final int value;
	
	public final String name;
}
