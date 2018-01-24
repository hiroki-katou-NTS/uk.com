package nts.uk.ctx.at.request.dom.application;
/**
 * 表示かどうか
 * @author dudt
 *
 */
public enum DisplayFlg {
	/**
	 * 使用しない
	 */
	NOTDISPLAY(0),
	/**
	 * 使用する
	 */
	DISPLAY(1);
	
	public int value;
	
	DisplayFlg(int type){
		this.value = type;
	}
}
