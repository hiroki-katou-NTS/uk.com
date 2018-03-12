package nts.uk.ctx.workflow.pub.service.export;

/**
 * @author loivt
 * 基準社員のルートの状況
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
