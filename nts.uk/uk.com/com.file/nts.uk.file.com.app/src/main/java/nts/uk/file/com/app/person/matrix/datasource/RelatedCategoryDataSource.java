package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

/**
 * RelatedCategoryDataSource
 * @author lanlt
 *
 */
@Getter
@Setter
public class RelatedCategoryDataSource extends DataTypeStateDataSource {
	private String relatedCtgCode;

	public RelatedCategoryDataSource(String relatedCtgCode) {
		super();
		this.dataTypeValue = DataTypeValue.RELATE_CATEGORY.value;
		this.relatedCtgCode = relatedCtgCode;
	}

	public static RelatedCategoryDataSource createFromJavaType(String relatedCtgCode) {
		return new RelatedCategoryDataSource(relatedCtgCode);
	}
}
