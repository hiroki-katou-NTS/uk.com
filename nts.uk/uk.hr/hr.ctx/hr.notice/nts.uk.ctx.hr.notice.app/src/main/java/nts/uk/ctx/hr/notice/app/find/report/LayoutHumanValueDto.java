package nts.uk.ctx.hr.notice.app.find.report;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SingleItemDto;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.shr.pereg.app.ComboBoxObject;
@Data
@AllArgsConstructor
public class LayoutHumanValueDto {


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

	// contains some information of item for render control
	private DataTypeStateReportDto item;
	
	/*
	 *  showColor is true when category hasn't data, false when category has data. 
	 */
	private boolean showColor;

	private int type;
	
	// help button Id
	private String resourceId;

	public LayoutHumanValueDto(String categoryId, String categoryCode, String itemDefId, String itemName,
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

	public LayoutHumanValueDto() {

	}

	public static LayoutHumanValueDto cloneFromItemDef(PersonInfoCategory perInfoCategory,
			PerInfoItemDefDto itemDef) {
		LayoutHumanValueDto dataObject = new LayoutHumanValueDto();

		dataObject.setCategoryId(itemDef.getPerInfoCtgId());
		dataObject.setCategoryCode(perInfoCategory.getCategoryCode().v());
		dataObject.setCategoryName(perInfoCategory.getCategoryName().v());
		dataObject.setCtgType(perInfoCategory.getCategoryType().value);

		dataObject.setItemDefId(itemDef.getId());
		dataObject.setItemName(itemDef.getItemName());
		dataObject.setItemCode(itemDef.getItemCode());
		dataObject.setItemParentCode(itemDef.getItemParentCode());
		dataObject.setRow(0);
		dataObject.setRequired(itemDef.getIsRequired() == 1);
		dataObject.setShowColor(true);
		
		//2018/02/11
		dataObject.setDispOrder(itemDef.getDispOrder());

		dataObject.setType(itemDef.getItemTypeState().getItemType());
		dataObject.setCtgType(perInfoCategory.getCategoryType().value);
		if (itemDef.getItemTypeState().getItemType() == ItemType.SINGLE_ITEM.value) {
			SingleItemReportDto sigleItem = (SingleItemDto) itemDef.getItemTypeState();
			dataObject.setItem(sigleItem.getDataTypeState());
		}
		dataObject.setResourceId(itemDef.getResourceId());
		return dataObject;
	}

	public static LayoutHumanValueDto initData(PerInfoItemDefForLayoutDto itemDef, PersonInfoCategory perInfoCategory) {
		LayoutHumanValueDto dataObject = new LayoutHumanValueDto();
		dataObject.setRecordId(itemDef.getRecordId());
		dataObject.setLstComboBoxValue(itemDef.getLstComboxBoxValue());
		dataObject.setCategoryId(itemDef.getPerInfoCtgId());
		dataObject.setCategoryCode(itemDef.getPerInfoCtgCd());
		dataObject.setCategoryName(perInfoCategory.getCategoryName().v());
		dataObject.setCtgType(perInfoCategory.getCategoryType().value);
		dataObject.setItemDefId(itemDef.getId());
		dataObject.setItemName(itemDef.getItemName());
		dataObject.setItemCode(itemDef.getItemCode());
		dataObject.setItemParentCode(itemDef.getItemParentCode());
		dataObject.setRow(itemDef.getRow());
		dataObject.setCtgType(itemDef.getCtgType());
		dataObject.setRequired(itemDef.getIsRequired() == 1);
		dataObject.setResourceId(itemDef.getResourceId());

		dataObject.setType(itemDef.getItemTypeState().getItemType());

		if (itemDef.getItemTypeState().getItemType() == 2) {
			SingleItemReportDto sigleItem = (SingleItemReportDto) itemDef.getItemTypeState();
			dataObject.setItem(sigleItem.getDataTypeState());
		}
		dataObject.setActionRole(itemDef.getActionRole());
		return dataObject;
	}
	
	public static LayoutHumanValueDto createFromDefItem(PersonInfoCategory perInfoCategory, PerInfoItemDefDto itemDef) {
		LayoutHumanValueDto item = new LayoutHumanValueDto();
		
		item.setCategoryId(itemDef.getPerInfoCtgId());
		item.setCtgType(perInfoCategory.getCategoryType().value);
		item.setCategoryCode(perInfoCategory.getCategoryCode().v());
		
		item.setItemDefId(itemDef.getId());
		item.setItemName(itemDef.getItemName());
		item.setItemCode(itemDef.getItemCode());
		item.setItemParentCode(itemDef.getItemParentCode());
		
		item.setRow(0);
		item.setRequired(itemDef.getIsRequired() == 1);
		item.setType(itemDef.getItemTypeState().getItemType());
		item.setResourceId(itemDef.getResourceId());
		return item;
	}
	
	public void toStringValue() {
		this.value = this.value.toString();
	}
	
	public boolean isComboBoxItem() {

		if (item != null) {
			int itemType = item.getDataTypeValue();
			if (itemType == DataTypeValueReport.SELECTION.value || itemType == DataTypeValueReport.SELECTION_BUTTON.value
					|| itemType == DataTypeValueReport.SELECTION_RADIO.value) {
				return true;
			}
		}

		return false;
	}
	
	public boolean isSelectionItem() {

		if (item != null) {
			int itemType = item.getDataTypeValue();
			if (itemType == DataTypeValueReport.SELECTION.value) {
				return true;
			}
		}

		return false;
	}
}
