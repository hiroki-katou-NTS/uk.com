package nts.uk.ctx.hr.notice.dom.report.valueImported;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatedCategoryImport extends DataTypeStateImport {
	private String relatedCtgCode;

	public RelatedCategoryImport(String relatedCtgCode) {
		super();
		this.dataTypeValue = DataTypeValueImport.RELATE_CATEGORY.value;
		this.relatedCtgCode = relatedCtgCode;
	}

	public static RelatedCategoryImport createFromJavaType(String relatedCtgCode) {
		return new RelatedCategoryImport(relatedCtgCode);
	}

}
