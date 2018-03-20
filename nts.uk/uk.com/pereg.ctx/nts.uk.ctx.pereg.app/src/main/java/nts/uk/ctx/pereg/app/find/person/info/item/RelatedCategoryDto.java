package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;

@Getter
public class RelatedCategoryDto extends DataTypeStateDto{
	private String relatedCode;	
	public RelatedCategoryDto(String relatedCode) {
		super();
		this.relatedCode = relatedCode;
	}

	public static RelatedCategoryDto createFromJavaType(String relatedCode) {
		return new RelatedCategoryDto(relatedCode);
	}
	
}
