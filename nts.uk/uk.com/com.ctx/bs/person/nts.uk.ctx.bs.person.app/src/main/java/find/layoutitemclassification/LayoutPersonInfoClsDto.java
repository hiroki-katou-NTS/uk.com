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

	private String layoutID;
	private int dispOrder;
	private String personInfoCategoryID;
	private int layoutItemType;
	private List<PerInfoItemDefDto> listItemDfDto;

	public static LayoutPersonInfoClsDto fromDomain(LayoutPersonInfoClassification domain,
			List<PerInfoItemDefDto> lst) {

		return new LayoutPersonInfoClsDto(domain.getLayoutID(), domain.getDispOrder().v(),
				domain.getPersonInfoCategoryID(), domain.getLayoutItemType().value, lst);
	}
}
