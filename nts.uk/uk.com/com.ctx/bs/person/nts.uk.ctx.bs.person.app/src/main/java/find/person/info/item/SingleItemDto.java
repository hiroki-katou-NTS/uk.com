package find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemType;

@Getter
public class SingleItemDto extends ItemTypeStateDto {

	protected DataTypeStateDto dataTypeState;

	private SingleItemDto(DataTypeStateDto dataTypeState) {
		super();
		this.itemType = ItemType.SINGLE_ITEM.value;
		this.dataTypeState = dataTypeState;
	}

	public static SingleItemDto createFromJavaType(DataTypeStateDto dataTypeState) {
		return new SingleItemDto(dataTypeState);
	}
}
