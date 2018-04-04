package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
@Setter
public class RelatedCategoryDto extends DataTypeStateDto {
	private String relatedCtgCode;

	public RelatedCategoryDto(String relatedCtgCode) {
		super();
		this.dataTypeValue = DataTypeValue.RELATE_CATEGORY.value;
		this.relatedCtgCode = relatedCtgCode;
	}

	public static RelatedCategoryDto createFromJavaType(String relatedCtgCode) {
		return new RelatedCategoryDto(relatedCtgCode);
	}

}
