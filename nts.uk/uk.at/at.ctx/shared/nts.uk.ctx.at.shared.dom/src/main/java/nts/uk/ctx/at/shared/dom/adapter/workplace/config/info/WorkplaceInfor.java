package nts.uk.ctx.at.shared.dom.adapter.workplace.config.info;

import lombok.Value;

@Value
public class WorkplaceInfor {

	private String workplaceId;
	private String hierarchyCode;
	private String workplaceCode;
	private String workplaceName;
	private String workplaceDisplayName;
	private String workplaceGenericName;
	private String workplaceExternalCode;

}