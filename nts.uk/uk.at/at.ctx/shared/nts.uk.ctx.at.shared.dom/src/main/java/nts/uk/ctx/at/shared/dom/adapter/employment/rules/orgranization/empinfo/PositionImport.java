package nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo;

import lombok.Builder;
import lombok.Data;

/**
 * The Class PositionExport.
 */
// 所属職位
@Data
@Builder
public class PositionImport {

	/** The position id. */
	private String positionId; // 職位ID

	/** The position code. */
	private String positionCode; // 職位コード
	
	/** The position name. */
	private String positionName; // 職位名称
}