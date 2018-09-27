/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.position;

import lombok.Builder;
import lombok.Data;

/**
 * The Class PositionModel.
 */
// 所属職位
@Data
@Builder
public class PositionModel {

	/** The position id. */
	private String positionId; // 職位ID

	/** The position code. */
	private String positionCode; // 職位コード
	
	/** The position name. */
	private String positionName; // 職位名称
}
