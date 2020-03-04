package nts.uk.ctx.hr.shared.dom.adapter;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WorkplaceImport {
	/** The workplace id. */
	private String workplaceId; // 職場ID

	/** The workplace code. */
	private String workplaceCode; // 職場コード

	/** The workplace generic name. */
	private String workplaceGenericName; // 職場総称

	/** The workplace name. */
	private String workplaceName; // 職場表示名

	public WorkplaceImport(String workplaceId, String workplaceCode, String workplaceGenericName,
			String workplaceName) {
		super();
		this.workplaceId = workplaceId;
		this.workplaceCode = workplaceCode;
		this.workplaceGenericName = workplaceGenericName;
		this.workplaceName = workplaceName;
	}
}
