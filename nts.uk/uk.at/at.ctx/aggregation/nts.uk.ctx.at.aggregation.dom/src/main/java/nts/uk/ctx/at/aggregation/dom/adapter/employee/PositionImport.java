package nts.uk.ctx.at.aggregation.dom.adapter.employee;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class PositionImport {
	
	/** The position id. */
	private String positionId; // 職位ID
	
	/** The position code. */
	private String positionCode; // 職位コード
	
	/** The position name. */
	private String positionName; // 職位名称
}
