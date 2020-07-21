package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkplaceInforParam {
	/** 職場ID */
	private String workplaceId;
	private String hierarchyCode;
	
	/** 職場コード */
	private String workplaceCode;
	
	/** 職場名称 */
	private String workplaceName;
	private String displayName;
	private String genericName;
	private String externalCode;
}
