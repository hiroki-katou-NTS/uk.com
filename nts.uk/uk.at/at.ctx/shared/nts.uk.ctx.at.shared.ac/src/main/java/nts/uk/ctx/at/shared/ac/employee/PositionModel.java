package nts.uk.ctx.at.shared.ac.employee;

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
