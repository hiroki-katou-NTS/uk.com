package nts.uk.ctx.at.record.dom.stamp.application;

import lombok.Getter;

@Getter
public class StampAttenDisplay {
	/** 会社ID */
	private final String companyId;
	
	/** 表示項目一覧 */
	private int displayItemId;

	public StampAttenDisplay(String companyId, int displayItemId) {
		super();
		this.companyId = companyId;
		this.displayItemId = displayItemId;
	}
	
	
}
