package nts.uk.ctx.pereg.app.find.layoutdef.classification;

import java.util.List;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nts.uk.ctx.pereg.app.find.person.info.item.DataTypeStateDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SingleItemDto;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.shr.pereg.app.ComboBoxObject;

@Data
@RequiredArgsConstructor
public class LayoutPersonInfoValueDto {


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
	private DataTypeStateDto item;
	
	/*
	 *  showColor is true when category hasn't data, false when category has data. 
	 */
	private boolean showColor;

	private int type;
	
	// help button Id
	private String resourceId;

	public LayoutPersonInfoValueDto(String categoryId, String categoryCode, String itemDefId, String itemName,
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

	public LayoutPersonInfoValueDto() {

	}

	public static LayoutPersonInfoValueDto cloneFromItemDef(PersonInfoCategory perInfoCategory,
			PerInfoItemDefDto itemDef) {
		LayoutPersonInfoValueDto dataObject = new LayoutPersonInfoValueDto();

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
			SingleItemDto sigleItem = (SingleItemDto) itemDef.getItemTypeState();
			dataObject.setItem(sigleItem.getDataTypeState());
		}
		dataObject.setResourceId(itemDef.getResourceId());
		return dataObject;
	}

	public static LayoutPersonInfoValueDto initData(PerInfoItemDefForLayoutDto itemDef, PersonInfoCategory perInfoCategory) {
		LayoutPersonInfoValueDto dataObject = new LayoutPersonInfoValueDto();
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
			SingleItemDto sigleItem = (SingleItemDto) itemDef.getItemTypeState();
			dataObject.setItem(sigleItem.getDataTypeState());
		}
		dataObject.setActionRole(itemDef.getActionRole());
		return dataObject;
	}
	
	public static LayoutPersonInfoValueDto createFromDefItem(PersonInfoCategory perInfoCategory, PerInfoItemDefDto itemDef) {
		LayoutPersonInfoValueDto item = new LayoutPersonInfoValueDto();
		
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
		
		item.setActionRole(ActionRole.EDIT);
		item.setResourceId(itemDef.getResourceId());
		return item;
	}
	
	public void toStringValue() {
		this.value = this.value.toString();
	}
	
	public boolean isComboBoxItem() {

		if (item != null) {
			int itemType = item.getDataTypeValue();
			if (itemType == DataTypeValue.SELECTION.value || itemType == DataTypeValue.SELECTION_BUTTON.value
					|| itemType == DataTypeValue.SELECTION_RADIO.value) {
				return true;
			}
		}

		return false;
	}
	
	public boolean isSelectionItem() {

		if (item != null) {
			int itemType = item.getDataTypeValue();
			if (itemType == DataTypeValue.SELECTION.value) {
				return true;
			}
		}

		return false;
	}
}
