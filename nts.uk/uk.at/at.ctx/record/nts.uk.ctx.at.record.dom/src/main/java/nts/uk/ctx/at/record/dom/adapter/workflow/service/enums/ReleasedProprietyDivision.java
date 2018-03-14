/**
 * 9:09:10 AM Mar 12, 2018
 */
package nts.uk.ctx.at.record.dom.adapter.workflow.service.enums;

/**
 * @author hungnm
 *
 */
public enum ReleasedProprietyDivision {
	/**
	 * 解除できない
	 */
	NOT_RELEASE(0),
	/**
	 * 解除できる
	 */
	RELEASE(1);
	public final int value;
	
	private ReleasedProprietyDivision(int value){
		this.value = value;
	}
}
