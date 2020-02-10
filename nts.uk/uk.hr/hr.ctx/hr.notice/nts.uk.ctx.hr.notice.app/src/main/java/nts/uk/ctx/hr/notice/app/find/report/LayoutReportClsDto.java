package nts.uk.ctx.hr.notice.app.find.report;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.hr.notice.dom.report.valueImported.PerInfoItemDefImport;

@Data
@AllArgsConstructor
public class LayoutReportClsDto {
	
	private int layoutID;

	private int dispOrder;

	private String className;

	private String personInfoCategoryID;

	private String categoryCode;
	
	private String categoryName;

	private int ctgType;
	
	private LayoutReportItemType layoutItemType;
	
	// None require
	private List<PerInfoItemDefImport> listItemDf;
	
	/*
	 * single: [{value: undefined}]
	 * -------------------------------------------------------------------------
	 * set: [ { value: undefined }, { value: undefined }, { value: undefined }]
	 * -------------------------------------------------------------------------
	 * list: [ { value: undefined }, { value: undefined }, { value: undefined }]
	 * -------------------------------------------------------------------------
	 */
	private List<LayoutHumanInfoValueDto> items;
	
	
	public LayoutReportClsDto() {
		items = new ArrayList<>();
	}

	public LayoutReportClsDto(int layoutID, int dispOrder, String personInfoCategoryID, int layoutItemType) {
		super();
		this.layoutID = layoutID;
		this.dispOrder = dispOrder;
		this.personInfoCategoryID = personInfoCategoryID;
		this.layoutItemType = EnumAdaptor.valueOf(layoutItemType, LayoutReportItemType.class);
	}

	public LayoutReportClsDto(int layoutID, int dispOrder, String personInfoCategoryID, int layoutItemType,
			String personInfoCategoryCD, int ctgType, String categoryName) {
		super();
		this.layoutID = layoutID;
		this.dispOrder = dispOrder;
		this.personInfoCategoryID = personInfoCategoryID;
		this.layoutItemType = EnumAdaptor.valueOf(layoutItemType, LayoutReportItemType.class);
		this.categoryCode = personInfoCategoryCD;
		this.categoryName = categoryName;
		this.ctgType = ctgType;
	}

	
//	public static LayoutReportClsDto cloneFromItemDef(PerInfoCtgShowImport perInfoCategory,
//			PerInfoItemDefImport itemDef) {
//		LayoutReportClsDto dataObject = new LayoutReportClsDto();
//
//		dataObject.setPersonInfoCategoryID(itemDef.getPerInfoCtgId());
//		dataObject.setCategoryCode(perInfoCategory.getCategoryCode());
//		dataObject.setCategoryName(perInfoCategory.getCategoryName());
//		dataObject.setCtgType(perInfoCategory.getCategoryType());
//
//		dataObject.setItemId(itemDef.getId());
//		dataObject.setItemName(itemDef.getItemName());
//		dataObject.setItemCode(itemDef.getItemCode());
//		dataObject.setItemParentCode(itemDef.getItemParentCode());
//		dataObject.setRow(0);
//		dataObject.setRequired(itemDef.getIsRequired() == 1);
//		dataObject.setShowColor(true);
//		
//		//2018/02/11
//		dataObject.setDispOrder(itemDef.getDispOrder());
//
//		dataObject.setType(itemDef.getItemTypeState().getItemType());
//		dataObject.setCtgType(perInfoCategory.getCategoryType().value);
//		if (itemDef.getItemTypeState().getItemType() == ItemType.SINGLE_ITEM.value) {
//			SingleItemDto sigleItem = (SingleItemDto) itemDef.getItemTypeState();
//			dataObject.setItem(sigleItem.getDataTypeState());
//		}
//		dataObject.setResourceId(itemDef.getResourceId());
//		dataObject.setInitValue(itemDef.getInitValue());
//		return dataObject;
//	}
	
	
//	public static LayoutReportClsDto fromDomain(RegisterPersonalReportItem domain, String ctgId) {
//		return new LayoutReportClsDto(domain.getPReportClsId(), domain.getDisplayOrder(),
//				ctgId, domain.getItemType());
//	}
//	public static LayoutReportClsDto fromDomainWithCtgCD(LayoutPersonInfoClassificationWithCtgCd domain) {
//		return new LayoutReportClsDto(domain.getLayoutID(), domain.getDispOrder().v(),
//				domain.getPersonInfoCategoryID(), domain.getLayoutItemType().value, domain.getPersonInfoCategoryCD(),
//				domain.getCtgType());
//	}
}
