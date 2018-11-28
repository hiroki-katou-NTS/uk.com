/**
 * 9:05:45 AM Mar 12, 2018
 */
package nts.uk.ctx.at.record.dom.adapter.workflow.service.enums;

/**
 * 基準社員のルート状況
 * @author hungnm
 *
 */
public enum ApproverEmployeeState {
	/**
	 * 完了
	 */
	COMPLETE(0),
	/**
	 * フェーズ通過済み
	 */
	PHASE_PASS(1),
	/**
	 * フェーズ最中
	 */
	PHASE_DURING(2),
	/**
	 * フェーズ未到達
	 */
	PHASE_LESS(3);
	
	public final int value;
	
	private ApproverEmployeeState(int value){
		this.value = value;
	}

}
