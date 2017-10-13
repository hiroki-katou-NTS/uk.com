/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.objectvalue;

/**
 * @author danpv
 *
 */
public enum ExecutedMenu {
	
	// 選択して実行
	SelectAndRun(0), 
	
	// ケース別実行
	ExecutionByCase(1);

	public final int value;

	private ExecutedMenu(int value) {
		this.value = value;
	}
}
