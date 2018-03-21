package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class RelatedCategoryDto extends DataTypeStateDto{
	private String relatedCode;	
	public RelatedCategoryDto(String relatedCode) {
		super();
		this.dataTypeValue = DataTypeValue.RELATE_CATEGORY.value;
		this.relatedCode = relatedCode;
	}

	public static RelatedCategoryDto createFromJavaType(String relatedCode) {
		return new RelatedCategoryDto(relatedCode);
	}
	
}
