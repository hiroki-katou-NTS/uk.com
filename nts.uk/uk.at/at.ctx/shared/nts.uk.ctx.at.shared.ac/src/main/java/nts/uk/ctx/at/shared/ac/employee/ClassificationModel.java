package nts.uk.ctx.at.shared.ac.employee;

import lombok.Builder;
import lombok.Data;

/**
 * The Class ClassificationModel.
 */
// 所属分類
@Data
@Builder
public class ClassificationModel {

	/** The classification code. */
	private String classificationCode; // 分類コード
	
	/** The classification name. */
	private String classificationName; // 分類名称
}
