package nts.uk.ctx.at.record.dom.workrecord.errorsetting;
/*
 * 補正結果
 */
public enum CorrectionResult {

	/*
	 *  失敗
	 */
	FAILURE(0),

	/*
	 *  成功
	 */
	SUCCESS(1);
	
	public int value;

	private CorrectionResult(int value) {
		this.value = value;
	}
}
