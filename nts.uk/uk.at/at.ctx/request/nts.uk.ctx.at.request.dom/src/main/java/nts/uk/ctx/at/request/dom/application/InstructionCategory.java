package nts.uk.ctx.at.request.dom.application;

import nts.arc.enums.EnumAdaptor;

public enum InstructionCategory {
	/**
	 * 残業
	 */
	OVERTIME(0),
	/**
	 * 休出
	 */
	HOLIDAYWORK(1);
	
	public final int value;
	
	InstructionCategory(int value){
		this.value = value;
	}
	public static InstructionCategory toEnum(int value){
		return EnumAdaptor.valueOf(value, InstructionCategory.class);
	}
}
