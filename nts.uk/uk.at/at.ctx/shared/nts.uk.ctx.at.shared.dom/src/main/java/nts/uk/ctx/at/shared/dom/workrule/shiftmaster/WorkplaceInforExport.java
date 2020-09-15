package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import lombok.Value;

@Value
public class WorkplaceInforExport {
	private String workplaceId;
	private String hierarchyCode;
	private String workplaceCode;
	private String workplaceName;
	private String workplaceDisplayName;
	private String workplaceGenericName;
	private String workplaceExternalCode;

}
