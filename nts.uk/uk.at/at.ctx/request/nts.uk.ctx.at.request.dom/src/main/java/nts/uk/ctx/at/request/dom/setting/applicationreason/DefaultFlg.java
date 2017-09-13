package nts.uk.ctx.at.request.dom.setting.applicationreason;
/**
 * 既定
 * @author dudt
 *
 */
public enum DefaultFlg {
	/**
	 * 使用しない
	 */
	NOTDEFAULT(0),
	/**
	 * 使用する
	 */
	DEFAULT(1);
	
	public int value;
	
	DefaultFlg(int type){
		this.value = type;
	}
}
