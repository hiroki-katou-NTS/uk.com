package nts.uk.ctx.workflow.pub.service.export;

/**
 * @author loivt
 * 解除可否区分
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
