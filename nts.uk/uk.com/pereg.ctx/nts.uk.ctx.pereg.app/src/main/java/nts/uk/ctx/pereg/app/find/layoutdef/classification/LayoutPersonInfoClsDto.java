/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layoutdef.classification;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutItemType;
import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutPersonInfoClassification;

@Data
@AllArgsConstructor

public class LayoutPersonInfoClsDto {

	private String layoutID;
	
	private int dispOrder;
	
	private String className;
	
	private String personInfoCategoryID;
	
	private LayoutItemType layoutItemType;
	
	// None require
	private List<PerInfoItemDefDto> listItemDf;

	/*
	 * List item value single/set: List<LayoutPersonInfoValueDto> list:
	 * List<List<LayoutPersonInfoValueDto>> ----------------------------- single:
	 * [{value: undefined}]
	 * -------------------------------------------------------------------------
	 * set: [ { value: undefined }, { value: undefined }, { value: undefined }]
	 * -------------------------------------------------------------------------
	 * list: [
	 * [ { value: undefined }, { value: undefined }, { value: undefined }]
	 * [ { value: undefined }, { value: undefined }, { value: undefined }]
	 * [ { value: undefined }, { value: undefined }, { value: undefined }]
	 * ]
	 * -------------------------------------------------------------------------
	 */
	private List<Object> items;
	
	public LayoutPersonInfoClsDto(){
		items = new ArrayList<>();
	}

	public LayoutPersonInfoClsDto(String layoutID, int dispOrder, String personInfoCategoryID, int layoutItemType) {
		super();
		this.layoutID = layoutID;
		this.dispOrder = dispOrder;
		this.personInfoCategoryID = personInfoCategoryID;
		this.layoutItemType = EnumAdaptor.valueOf(layoutItemType, LayoutItemType.class);
	}

	public static LayoutPersonInfoClsDto fromDomain(LayoutPersonInfoClassification domain) {
		return new LayoutPersonInfoClsDto(domain.getLayoutID(), domain.getDispOrder().v(),
				domain.getPersonInfoCategoryID(), domain.getLayoutItemType().value);
	}

}
