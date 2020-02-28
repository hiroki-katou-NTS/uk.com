package nts.uk.ctx.hr.shared.dom.adapter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassificationImport {
	/** The classification code. */
	private String classificationCode; // 分類コード
	
	/** The classification name. */
	private String classificationName; // 分類名称

	public ClassificationImport(String classificationCode, String classificationName) {
		super();
		this.classificationCode = classificationCode;
		this.classificationName = classificationName;
	}
	
}
