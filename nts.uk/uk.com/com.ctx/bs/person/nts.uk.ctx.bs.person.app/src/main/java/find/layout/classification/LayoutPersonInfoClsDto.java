/**
 * 
 */
package find.layout.classification;

import java.util.List;

import find.person.info.item.PerInfoItemDefDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.bs.person.dom.person.layout.classification.LayoutPersonInfoClassification;

@Data
@AllArgsConstructor
public class LayoutPersonInfoClsDto {

	private String layoutID;
	private int dispOrder;
	private String className;
	private String personInfoCategoryID;
	private int layoutItemType;
	private List<PerInfoItemDefDto> listItemDf;

	/* List item value
	 * single/set: List<LayoutPersonInfoValueDto>
	 * list: List<List<LayoutPersonInfoValueDto>>
	 *-----------------------------	
	 * single: [{value: undefined}]
	 *-------------------------------------------------------------------------
	 * set: [ { value: undefined }, { value: undefined }, { value: undefined }]
	 *-------------------------------------------------------------------------
	 * list: [
	 * [ { value: undefined }, { value: undefined }, { value: undefined }]
	 * [ { value: undefined }, { value: undefined }, { value: undefined }]
	 * [ { value: undefined }, { value: undefined }, { value: undefined }]
	 * ]
	 *-------------------------------------------------------------------------
	 */
	private List<Object> items;	
	
	public LayoutPersonInfoClsDto(String layoutID, int dispOrder, String personInfoCategoryID, int layoutItemType) {
		super();
		this.layoutID = layoutID;
		this.dispOrder = dispOrder;
		this.personInfoCategoryID = personInfoCategoryID;
		this.layoutItemType = layoutItemType;
	}	

	public static LayoutPersonInfoClsDto fromDomain(LayoutPersonInfoClassification domain) {
		return new LayoutPersonInfoClsDto(domain.getLayoutID(), domain.getDispOrder().v(),
				domain.getPersonInfoCategoryID(), domain.getLayoutItemType().value);
	}

}
