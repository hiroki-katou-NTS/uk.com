/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave;

import lombok.Builder;
import lombok.Data;

/**
 * The Class ChildNursingRemainExport.
 */
@Data
@Builder
//å­�çœ‹è­·ã�®æ®‹æ•°æƒ…å ±
public class ChildNursingRemainInforExport {

//	æ®‹æ•°
	private Double residual;
	
//	ä½¿ç”¨æ•°
	private Double numberOfUse;
}
