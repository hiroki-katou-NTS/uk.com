package nts.uk.ctx.at.schedule.dom.appreflectprocess.gobacksche;

import nts.arc.enums.EnumAdaptor;
/**
 * 勤務を変更する
 * @author dudt
 *
 */
public enum ChangeAtrAppGoback {
	/**
	 * 勤務を変更しない
	 */
	NOTCHANGE(0),
	/**
	 * 勤務を変更する
	 */
	CHANGE(1);
	
	public int value;
	
	ChangeAtrAppGoback(int type){
		this.value = type;
	}
	
	public static ChangeAtrAppGoback toEnum(int value){
		return EnumAdaptor.valueOf(value, ChangeAtrAppGoback.class);
	}
}
