package nts.uk.ctx.pereg.dom.person.info.item;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ItemType {

	// 1:セット項目(SetItem)
	SET_ITEM(1),

	// 2:単体項目(SingleItem)
	SINGLE_ITEM(2);

	public final int value;
}
