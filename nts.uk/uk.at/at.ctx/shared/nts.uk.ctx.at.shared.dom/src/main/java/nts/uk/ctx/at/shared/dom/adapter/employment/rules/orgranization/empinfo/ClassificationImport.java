package nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * The Class ClassificationImport.
 */
// 所属分類
@AllArgsConstructor
@Data
public class ClassificationImport {

	/** The classification code. */
	private String classificationCode; // 分類コード
	
	/** The classification name. */
	private String classificationName; // 分類名称
}