package nts.uk.ctx.at.record.dom.workinformation.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CalculationState {
	
	/* 計算済み*/
	Calculated(0),
	/* 未計算*/
	No_Calculated(1);
	
	public final int value;

}
