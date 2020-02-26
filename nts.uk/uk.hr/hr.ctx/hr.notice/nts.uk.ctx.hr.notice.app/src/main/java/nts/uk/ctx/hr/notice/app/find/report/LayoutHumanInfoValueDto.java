package nts.uk.ctx.hr.notice.app.find.report;

import java.util.List;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nts.uk.ctx.hr.notice.dom.report.valueImported.DataTypeStateImport;
import nts.uk.ctx.hr.notice.dom.report.valueImported.DataTypeValueImport;
import nts.uk.ctx.hr.notice.dom.report.valueImported.PerInfoItemDefImport;
import nts.uk.ctx.hr.notice.dom.report.valueImported.SingleItemImport;
import nts.uk.ctx.hr.notice.dom.report.valueImported.ctg.PerInfoCtgShowImport;
import nts.uk.shr.pereg.app.ComboBoxObject;

@Data
@RequiredArgsConstructor
public class LayoutHumanInfoValueDto {

	private String recordId;

	// categoryID
	@NonNull
	private String categoryId;

	// categoryCode
	@NonNull
	private String categoryCode;
	
	// categoryName 
	private String categoryName;
	
	private int ctgType;

	// itemDefID
	@NonNull
	private String itemDefId;

	// for label text
	@NonNull
	private String itemName;

	// for label constraint
	@NonNull
	private String itemCode;

	// for new set item structor
	private String itemParentCode;
	
	// is not ItemDefinition of attribute
	private int dispOrder;

	@NonNull
	// index of item in list (multiple, history)
	private Integer row;

	// value of item definition
	private Object value;
	
	// text value if button control
	private String textValue;

	// is required?
	// for render control label
	private boolean required;

	/**
	 * combo box value list when item type selection
	 */
	
	private List<ComboBoxObject> lstComboBoxValue;
	
	/*
	 * hidden value - view only - can edit
	 */
	private ActionRole actionRole;
	

	// contains some information of item for render control
	private DataTypeStateImport item;
	
	/*
	 *  showColor is true when category hasn't data, false when category has data. 
	 */
	private boolean showColor;

	private int type;
	
	// help button Id
	private String resourceId;
	
	// init value
	private String initValue;

	public LayoutHumanInfoValueDto(String categoryId, String categoryCode, String itemDefId, String itemName,
			String itemCode, String itemParentCode, Integer row, Object value) {
		this.categoryId = categoryId;
		this.categoryCode = categoryCode;
		this.itemDefId = itemDefId;
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.itemParentCode = itemParentCode;
		this.row = row;
		this.value = value;
	}

	public LayoutHumanInfoValueDto() {

	}

	public static LayoutHumanInfoValueDto cloneFromItemDef(PerInfoCtgShowImport perInfoCategory,
			PerInfoItemDefImport itemDef) {
		LayoutHumanInfoValueDto dataObject = new LayoutHumanInfoValueDto();

		dataObject.setCategoryId(itemDef.getPerInfoCtgId());
		dataObject.setCategoryCode(perInfoCategory.getCategoryCode());
		dataObject.setCategoryName(perInfoCategory.getCategoryName());
		dataObject.setCtgType(perInfoCategory.getCategoryType());

		dataObject.setItemDefId(itemDef.getId());
		dataObject.setItemName(itemDef.getItemName());
		dataObject.setItemCode(itemDef.getItemCode());
		dataObject.setItemParentCode(itemDef.getItemParentCode());
		dataObject.setRow(0);
		dataObject.setRequired(itemDef.getIsRequired() == 1);
		dataObject.setShowColor(true);
		dataObject.setActionRole(ActionRole.EDIT);
		//2018/02/11
		dataObject.setDispOrder(itemDef.getDispOrder());

		dataObject.setType(itemDef.getItemTypeState().getItemType());
		dataObject.setCtgType(perInfoCategory.getCategoryType());
		if (itemDef.getItemTypeState().getItemType() == 2) {
			SingleItemImport sigleItem = (SingleItemImport) itemDef.getItemTypeState();
			dataObject.setItem(sigleItem.getDataTypeState());
		}
		dataObject.setResourceId(itemDef.getResourceId());
		dataObject.setInitValue(null);
		return dataObject;
	}

//	public static LayoutHumanInfoValueDto initData(PerInfoItemDefForLayoutDto itemDef, PerInfoCtgShowImport perInfoCategory) {
//		LayoutHumanInfoValueDto dataObject = new LayoutHumanInfoValueDto();
//		dataObject.setRecordId(itemDef.getRecordId());
//		dataObject.setLstComboBoxValue(itemDef.getLstComboxBoxValue());
//		dataObject.setCategoryId(itemDef.getPerInfoCtgId());
//		dataObject.setCategoryCode(itemDef.getPerInfoCtgCd());
//		dataObject.setCategoryName(perInfoCategory.getCategoryName());
//		dataObject.setCtgType(perInfoCategory.getCategoryType());
//		dataObject.setItemDefId(itemDef.getId());
//		dataObject.setItemName(itemDef.getItemName());
//		dataObject.setItemCode(itemDef.getItemCode());
//		dataObject.setItemParentCode(itemDef.getItemParentCode());
//		dataObject.setRow(itemDef.getRow());
//		dataObject.setCtgType(itemDef.getCtgType());
//		dataObject.setRequired(itemDef.getIsRequired() == 1);
//		dataObject.setResourceId(itemDef.getResourceId());
//		dataObject.setInitValue(itemDef.getInitValue());
//		dataObject.setValue(itemDef.getInitValue());
//		dataObject.setType(itemDef.getItemTypeState().getItemType());
//
//		if (itemDef.getItemTypeState().getItemType() == 2) {
//			SingleItemImport sigleItem = (SingleItemImport) itemDef.getItemTypeState();
//			dataObject.setItem(sigleItem.getDataTypeState());
//		}
//		dataObject.setActionRole(itemDef.getActionRole());
//		return dataObject;
//	}
	
	public static LayoutHumanInfoValueDto createFromDefItem(PerInfoCtgShowImport perInfoCategory, PerInfoItemDefImport itemDef) {
		LayoutHumanInfoValueDto item = new LayoutHumanInfoValueDto();
		
		item.setCategoryId(itemDef.getPerInfoCtgId());
		item.setCtgType(perInfoCategory.getCategoryType());
		item.setCategoryCode(perInfoCategory.getCategoryCode());
		
		item.setItemDefId(itemDef.getId());
		item.setItemName(itemDef.getItemName());
		item.setItemCode(itemDef.getItemCode());
		item.setItemParentCode(itemDef.getItemParentCode());
		
		item.setRow(0);
		item.setRequired(itemDef.getIsRequired() == 1);
		item.setType(itemDef.getItemTypeState().getItemType());
		
		item.setActionRole(ActionRole.EDIT);
		item.setResourceId(itemDef.getResourceId());
		item.setInitValue(itemDef.getInitValue());
		item.setValue(itemDef.getInitValue());
		return item;
	}
	
	public void toStringValue() {
		this.value = this.value.toString();
	}
	
	public boolean isComboBoxItem() {

		if (item != null) {
			int itemType = item.getDataTypeValue();
			if (itemType == DataTypeValueImport.SELECTION.value || itemType == DataTypeValueImport.SELECTION_BUTTON.value
					|| itemType == DataTypeValueImport.SELECTION_RADIO.value) {
				return true;
			}
		}

		return false;
	}
	
	public boolean isSelectionItem() {

		if (item != null) {
			int itemType = item.getDataTypeValue();
			if (itemType == DataTypeValueImport.SELECTION.value) {
				return true;
			}
		}

		return false;
	}
}
