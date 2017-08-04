/**
 * 
 */
package find.layoutitemclassification;

import java.util.List;

import find.person.info.item.PerInfoItemDefDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.LayoutPersonInfoClassification;

@AllArgsConstructor
@Data
public class LayoutPersonInfoClsDto {

	String layoutID;
	int disPOrder;
	String personInfoCategoryID;
	int layoutItemType;
	List<PerInfoItemDefDto> listItemDfDto;

	public LayoutPersonInfoClsDto(String layoutID, int disPOrder, String personInfoCategoryID, int layoutItemType) {
		super();
		this.layoutID = layoutID;
		this.disPOrder = disPOrder;
		this.personInfoCategoryID = personInfoCategoryID;
		this.layoutItemType = layoutItemType;
	}

	public static LayoutPersonInfoClsDto fromDomain(LayoutPersonInfoClassification domain) {
		return new LayoutPersonInfoClsDto(domain.getLayoutID(), domain.getDisPOrder().v().intValue(),
				domain.getPersonInfoCategoryID(), domain.getLayoutItemType().value);

	}
}
