package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported;

import lombok.Builder;
import lombok.Data;

/**
 * The Class ClassificationExport.
 */
// 所属分類
@Data
@Builder
public class ClassificationImport {

	/** The classification code. */
	private String classificationCode; // 分類コード
	
	/** The classification name. */
	private String classificationName; // 分類名称
}