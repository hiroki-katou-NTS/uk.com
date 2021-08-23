package nts.uk.ctx.exio.dom.input.importableitem;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.errors.ErrorMessage;
import nts.uk.ctx.exio.dom.input.errors.ItemError;
import nts.uk.ctx.exio.dom.input.util.Either;

/**
 * 受入可能項目
 */
@Getter
@AllArgsConstructor
public class ImportableItem implements DomainAggregate{

	private ImportingDomainId domainId;
	private int itemNo;
	private String itemName;
	private ItemType itemType;
	private boolean required;
	private Optional<DomainConstraint> domainConstraint;

	public Optional<ErrorMessage> validate(DataItem dataItem) {

		if(required && dataItem.isEmpty()) {
			return Optional.of(new ErrorMessage("必須項目ですが受入データがありません。"));
		}

		return domainConstraint
				.flatMap(c -> c.validate(dataItem.getValue()));
	}

	/**
	 * 型を変換する
	 * @param value
	 * @return
	 */
	public Either<ItemError, ?> parse(String value) {
		return itemType.parse(value)
				.mapLeft(errorMessage -> new ItemError(itemNo, errorMessage.getText()));
	}
}
