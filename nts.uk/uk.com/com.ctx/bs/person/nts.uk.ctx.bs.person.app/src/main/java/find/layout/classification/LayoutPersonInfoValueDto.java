package find.layout.classification;

import find.person.info.item.DataTypeStateDto;
import find.person.info.item.PerInfoItemDefDto;
import find.person.info.item.SingleItemDto;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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

	@NonNull
	// value of item definition
	private Object value;

	// is required?
	// for render control label
	private boolean required;

	// containt some infor of item for
	// render control
	private DataTypeStateDto item;

	public LayoutPersonInfoValueDto() {

	}

	public static LayoutPersonInfoValueDto initData(String categoryCode, PerInfoItemDefDto itemDef, Object data) {
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
		return dataObject;
	}

}
