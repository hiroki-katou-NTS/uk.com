package nts.uk.ctx.at.schedule.pub.applicationreflectprocess.applicationsmanager;

import nts.arc.enums.EnumAdaptor;

public enum ChangeAtrAppGobackPub {
	/**
	 * 勤務を変更しない
	 */
	NOTCHANGE(0),
	/**
	 * 勤務を変更する
	 */
	CHANGE(1);
	
	public int value;
	
	ChangeAtrAppGobackPub(int type){
		this.value = type;
	}
	
	public static ChangeAtrAppGobackPub toEnum(int value){
		return EnumAdaptor.valueOf(value, ChangeAtrAppGobackPub.class);
	}
}
