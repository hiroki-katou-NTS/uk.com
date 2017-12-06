package nts.uk.ctx.pereg.app.find.layoutdef.classification;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nts.uk.ctx.pereg.app.find.person.info.item.DataTypeStateDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SingleItemDto;

@Data
@RequiredArgsConstructor
public class LayoutPersonInfoValueDto {

	// categoryID
	@NonNull
	private String categoryId;

	// categoryCode
	@NonNull
	private String categoryCode;

	// itemDefID
	@NonNull
	private String itemDefId;

	// for label text
	@NonNull
	private String itemName;

	// for label constraint
	@NonNull
	private String itemCode;

	@NonNull
	// index of item in list (multiple, history)
	private Integer row;

	// value of item definition
	private Object value;

	// is required?
	// for render control label
	private boolean required;

	/*
	 * hidden value - view only - can edit
	 */
	private ActionRole actionRole;

	// contains some information of item for render control
	private DataTypeStateDto item;

	public LayoutPersonInfoValueDto(String categoryId, String categoryCode, String itemDefId, String itemName,
			String itemCode, Integer row, Object value) {
		this.categoryId = categoryId;
		this.categoryCode = categoryCode;
		this.itemDefId = itemDefId;
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.row = row;
		this.value = value;
	}

	public LayoutPersonInfoValueDto() {

	}

	public static LayoutPersonInfoValueDto initData(String categoryCode, PerInfoItemDefDto itemDef, Object value) {
		LayoutPersonInfoValueDto dataObject = new LayoutPersonInfoValueDto();
		dataObject.setCategoryId(itemDef.getPerInfoCtgId());
		dataObject.setCategoryCode(categoryCode);
		dataObject.setItemDefId(itemDef.getId());
		dataObject.setItemName(itemDef.getItemName());
		dataObject.setItemCode(itemDef.getItemCode());
		dataObject.setRow(0);
		dataObject.setValue(value);
		dataObject.setRequired(itemDef.getIsRequired() == 1);
		SingleItemDto sigleItem = (SingleItemDto) itemDef.getItemTypeState();
		dataObject.setItem(sigleItem.getDataTypeState());
		return dataObject;
	}

	/**
	 * for test
	 */
	public static LayoutPersonInfoValueDto initData(PerInfoItemDefDto itemDef, Object data) {
		LayoutPersonInfoValueDto dataObject = new LayoutPersonInfoValueDto();
		dataObject.setCategoryId(itemDef.getPerInfoCtgId());
		dataObject.setItemDefId(itemDef.getId());
		dataObject.setItemName(itemDef.getItemName());
		dataObject.setItemCode(itemDef.getItemCode());
		dataObject.setRow(0);
		dataObject.setValue(data);
		dataObject.setRequired(itemDef.getIsRequired() == 1);
		SingleItemDto sigleItem = (SingleItemDto) itemDef.getItemTypeState();
		dataObject.setItem(sigleItem.getDataTypeState());
		return dataObject;
	}

	public static LayoutPersonInfoValueDto initData(String categoryCode, PerInfoItemDefDto itemDef, Object data,
			ActionRole actionRole) {
		LayoutPersonInfoValueDto dataObject = new LayoutPersonInfoValueDto();
		dataObject.setCategoryId(itemDef.getPerInfoCtgId());
		dataObject.setCategoryCode(categoryCode);
		dataObject.setItemDefId(itemDef.getId());
		dataObject.setItemName(itemDef.getItemName());
		dataObject.setItemCode(itemDef.getItemCode());
		dataObject.setRow(0);
		dataObject.setValue(data);
		dataObject.setRequired(itemDef.getIsRequired() == 1);
		SingleItemDto sigleItem = (SingleItemDto) itemDef.getItemTypeState();
		dataObject.setItem(sigleItem.getDataTypeState());
		dataObject.setActionRole(actionRole);
		return dataObject;
	}

	public static LayoutPersonInfoValueDto initData(PerInfoItemDefForLayoutDto itemDef, Object value) {
		LayoutPersonInfoValueDto dataObject = new LayoutPersonInfoValueDto();
		dataObject.setCategoryId(itemDef.getPerInfoCtgId());
		dataObject.setCategoryCode(itemDef.getPerInfoCtgCd());
		dataObject.setItemDefId(itemDef.getItemDefId());
		dataObject.setItemName(itemDef.getItemName());
		dataObject.setItemCode(itemDef.getItemCode());
		dataObject.setRow(itemDef.getRow());
		dataObject.setValue(value);
		dataObject.setRequired(itemDef.getIsRequired() == 1);
		SingleItemDto sigleItem = (SingleItemDto) itemDef.getItemTypeState();
		dataObject.setItem(sigleItem.getDataTypeState());
		dataObject.setActionRole(itemDef.getActionRole());
		return dataObject;
	}
}
