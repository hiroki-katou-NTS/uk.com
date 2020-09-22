package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CalculationState {
	
	/* 計算済み*/
	Calculated(0),
	/* 未計算*/
	No_Calculated(1);
	
	public final int value;

}
