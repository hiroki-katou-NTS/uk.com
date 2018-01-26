package nts.uk.ctx.at.request.dom.setting.workplace;

import nts.arc.enums.EnumAdaptor;

/**
 * 選択
 * @author dudt
 *
 */
public enum SelectionFlg {
	/**
	 * 使用しない
	 */
	NOTSELECTION(0),
	/**
	 * 使用する
	 */
	SELECTION(1);
	
	public int value;
	
	SelectionFlg(int type){
		this.value = type;
	}
	
	public static SelectionFlg toEnum(int value){
		return EnumAdaptor.valueOf(value, SelectionFlg.class);
	}
}
