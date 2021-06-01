package nts.uk.ctx.exio.dom.input.validation;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.revise.dataformat.ItemType;
import nts.uk.ctx.exio.dom.input.validation.condition.system.DomainConstraint;

/**
 * 受入可能項目の定義
 */
@Getter
public class ImportableItem implements DomainAggregate{

	private int groupId;
	private int itemNo;
	private ItemType itemType;
	private Optional<DomainConstraint> domainConstraint;
	
	public ImportableItem(int groupId, int itemNo, Optional<DomainConstraint> domainConstraint) {
		super();
		this.groupId = groupId;
		this.itemNo = itemNo;
		this.domainConstraint = domainConstraint;
	}
	
	public boolean validate(DataItem dataItem) {
		return domainConstraint.map(constraint -> constraint.validate(dataItem))
				//↓はそもそも制限が無いという意図.
				.orElse(true);
	}
}
