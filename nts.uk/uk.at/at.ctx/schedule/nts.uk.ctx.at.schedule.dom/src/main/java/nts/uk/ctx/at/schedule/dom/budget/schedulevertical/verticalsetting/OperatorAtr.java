package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * 計算式組込/演算子区分
 *
 */
@AllArgsConstructor
public enum OperatorAtr {
	/** 0- + **/
	SUMMATION(0, "Enum_OperatorAtr_ADD"),
	/** 1- - **/
	SUBTRACTION(1, "Enum_OperatorAtr_SUBTRACT"),	
	/** 2- x **/
	MULTIPLICATION(2, "Enum_OperatorAtr_MULTIPLY"),
	/** 3- ÷ **/
	DIVISION(3, "Enum_OperatorAtr_DIVIDE");
	
	public final int value;
	public final String nameId;
}
