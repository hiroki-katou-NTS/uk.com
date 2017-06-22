package nts.uk.ctx.at.record.dom.standardtime.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
public enum LaborSystemtAtr {
	
	/*
	 * ３６協定労働制
	 */
	//0: 一般労働制
	GENERAL_LABOR_SYSTEM(0),
	//1: 変形労働時間制
	DEFORMATION_WORKING_TIME_SYSTEM(1);
	
	public final int value;
	
}
