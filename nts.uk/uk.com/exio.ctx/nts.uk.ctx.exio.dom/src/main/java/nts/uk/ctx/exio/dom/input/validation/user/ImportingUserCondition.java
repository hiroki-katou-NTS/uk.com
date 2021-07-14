package nts.uk.ctx.exio.dom.input.validation.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.exio.dom.input.DataItem;

/**
 * 受入時のユーザ条件
 */
@AllArgsConstructor
@Getter
public class ImportingUserCondition implements DomainAggregate{

	/** 会社ID */
	private String companyId;

	/** 受入設定コード */
	private String settingCode;

	/** 項目No */
	private int itemNo;

	/** 条件 */
	private Validation validation;

	public boolean validate(DataItem target) {

		return validation.validate(target);

	}
}
