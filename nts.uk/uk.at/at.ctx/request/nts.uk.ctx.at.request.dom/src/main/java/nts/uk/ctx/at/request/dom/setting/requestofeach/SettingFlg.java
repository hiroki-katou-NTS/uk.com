package nts.uk.ctx.at.request.dom.setting.requestofeach;
/**
 * 設定
 * @author dudt
 *
 */
public enum SettingFlg {
	/**
	 * 使用しない
	 */
	NOTSETTING(0),
	/**
	 * 使用する
	 */
	SETTING(1);
	
	public int value;
	
	SettingFlg(int type){
		this.value = type;
	}
}
