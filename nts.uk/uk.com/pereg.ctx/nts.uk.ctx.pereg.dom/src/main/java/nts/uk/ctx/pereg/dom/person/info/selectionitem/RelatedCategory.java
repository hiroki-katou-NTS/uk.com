package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryCode;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
@Getter
public class RelatedCategory extends DataTypeState{
	private CategoryCode relatedCtgCode;
	private RelatedCategory(String relatedCtgText) {
		super();
		this.dataTypeValue = DataTypeValue.RELATE_CATEGORY;
		this.relatedCtgCode = new CategoryCode(relatedCtgText);
	}

	public static RelatedCategory createFromJavaType(String relatedCtgText) {
		return new RelatedCategory(relatedCtgText);
	}
}
