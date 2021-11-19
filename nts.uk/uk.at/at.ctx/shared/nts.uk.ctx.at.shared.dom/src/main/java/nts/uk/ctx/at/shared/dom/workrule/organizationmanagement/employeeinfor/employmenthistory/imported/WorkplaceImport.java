package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported;

import lombok.Builder;
import lombok.Data;

/**
 * The Class WorkplaceExport.
 */
// 所属職場
@Builder
@Data
public class WorkplaceImport {

	/** The workplace id. */
	private String workplaceId; // 職場ID

	/** The workplace code. */
	private String workplaceCode; //職場コード
	
	/** The workplace generic name. */
	private String workplaceGenericName; //職場総称
	
	/** The workplace name. */
	private String workplaceName; //職場表示名
}