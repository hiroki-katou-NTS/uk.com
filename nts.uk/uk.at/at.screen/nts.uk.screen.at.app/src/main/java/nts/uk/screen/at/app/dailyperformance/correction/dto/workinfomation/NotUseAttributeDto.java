package nts.uk.screen.at.app.dailyperformance.correction.dto.workinfomation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NotUseAttributeDto {
	
	/* 計算済み*/
	Calculated(0),
	/* 未計算*/
	No_Calculated(1);
	
	public final int value;

}