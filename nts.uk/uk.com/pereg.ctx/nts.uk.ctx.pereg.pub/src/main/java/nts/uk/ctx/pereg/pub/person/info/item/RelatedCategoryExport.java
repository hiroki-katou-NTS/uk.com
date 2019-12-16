package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatedCategoryExport extends DataTypeStateExport {
	private String relatedCtgCode;

	public RelatedCategoryExport(String relatedCtgCode) {
		super();
		this.dataTypeValue = DataTypeValueExport.RELATE_CATEGORY.value;
		this.relatedCtgCode = relatedCtgCode;
	}

	public static RelatedCategoryExport createFromJavaType(String relatedCtgCode) {
		return new RelatedCategoryExport(relatedCtgCode);
	}

}
