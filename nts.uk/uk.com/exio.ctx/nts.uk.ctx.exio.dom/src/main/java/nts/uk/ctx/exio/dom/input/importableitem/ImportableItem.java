package nts.uk.ctx.exio.dom.input.importableitem;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;

/**
 * 受入可能項目
 */
@Getter
@AllArgsConstructor
public class ImportableItem implements DomainAggregate{

	private ImportingGroupId groupId;
	private int itemNo;
	private String itemName;
	private ItemType itemType;
	private boolean required;
	private Optional<DomainConstraint> domainConstraint;

	public boolean validate(DataItem dataItem) {

		if(required && dataItem.isEmpty()) {
			return false;
		}


		return domainConstraint
				.map(c -> c.validate(dataItem.getValue()))
				//↓はそもそも制限が無いという意図.
				.orElse(true);
	}


	/**
	 * 型を変換する
	 * @param value
	 * @return
	 */
	public Object parse(String value) {
		return this.itemType.parse(value);
	}
}
