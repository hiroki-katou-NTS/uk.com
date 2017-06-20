package nts.uk.ctx.at.record.dom.standardtime.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
public enum LaborSystemtAtr {
	//0: ˆê”Ê˜J“­§
	GENERAL_LABOR_SYSTEM(0),
	//1: •ÏŒ`˜J“­ŠÔ§
	DEFORMATION_WORKING_TIME_SYSTEM(1);
	
	public final int value;
	
	public String toName(){
		String name;
		switch (value) {
		case 0:
			name = "ˆê”Ê˜J“­§";
			break;
		case 1:
			name = "Œ`˜J“­ŠÔ§";
			break;
		default:
			name = "ˆê”Ê˜J“­§";
			break;
		}
		
		return name;
	}
}
