package nts.uk.ctx.workflow.dom.adapter.workplace;

import lombok.Value;

@Value
public class WorkplaceImport {
	/** work place id*/
	private String wkpId;
	/** The workplace code. */
	private String wkpCode;

	/** The workplace name. */
	private String wkpName;
}
