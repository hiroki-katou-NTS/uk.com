package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OperatorAtr {
	/** 0- - **/
	SUBTRACTION(0),
	/** 1- + **/
	SUMMATION(1),
	/** 2- x **/
	MULTIPLICATION(2),
	/** 3- ÷ **/
	DIVISION(3);
	
	public final int value;
}
