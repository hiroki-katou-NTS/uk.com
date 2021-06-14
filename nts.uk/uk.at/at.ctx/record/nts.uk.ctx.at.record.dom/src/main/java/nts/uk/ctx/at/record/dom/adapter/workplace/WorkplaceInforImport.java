package nts.uk.ctx.at.record.dom.adapter.workplace;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkplaceInforImport {
	private String workplaceId;
	private String hierarchyCode;
	private String workplaceCode;
	private String workplaceName;
	private String workplaceDisplayName;
	private String workplaceGenericName;
	private String workplaceExternalCode;

}
