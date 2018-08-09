package nts.uk.ctx.workflow.dom.service.resultrecord;

import lombok.AllArgsConstructor;

/**
 * 解除可否区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum ReleaseDivision {
	
	/**
	 * 解除できない
	 */
	NOT_RELEASE(0, "解除できない"),
	/**
	 * 解除できる
	 */
	RELEASE(1, "解除できる");
	
	public final int value;
	
	public final String name;
	
}
