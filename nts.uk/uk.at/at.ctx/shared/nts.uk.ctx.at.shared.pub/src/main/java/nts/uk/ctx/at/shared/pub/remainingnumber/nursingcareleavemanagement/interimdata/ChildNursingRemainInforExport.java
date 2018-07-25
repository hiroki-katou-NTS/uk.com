/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.remainingnumber.nursingcareleavemanagement.interimdata;

import lombok.Builder;
import lombok.Data;

/**
 * The Class ChildNursingRemainExport.
 */
@Data
@Builder
//子看護の残数情報
public class ChildNursingRemainInforExport {

//	残数
	private Double residual;
	
//	使用数
	private Double numberOfUse;
}
