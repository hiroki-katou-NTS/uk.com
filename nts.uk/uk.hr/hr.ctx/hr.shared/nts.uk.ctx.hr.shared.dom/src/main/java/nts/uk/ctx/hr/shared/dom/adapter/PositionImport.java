package nts.uk.ctx.hr.shared.dom.adapter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PositionImport {
	/** The position id. */
	private String positionId; // 職位ID

	/** The position code. */
	private String positionCode; // 職位コード
	
	/** The position name. */
	private String positionName; // 職位名称

	public PositionImport(String positionId, String positionCode, String positionName) {
		super();
		this.positionId = positionId;
		this.positionCode = positionCode;
		this.positionName = positionName;
	}
	
}
