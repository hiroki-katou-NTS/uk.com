package nts.uk.ctx.at.request.dom.application.applist.service.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemName;
import nts.uk.ctx.at.shared.dom.scherec.optitem.UnitOfOptionalItem;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class OptionalItemOutput {
	/**
	 * 任意項目名称
	 */
	private OptionalItemName optionalItemName;
	
	/**
	 * 値
	 */
	private AnyItemValue anyItemValue;
	
	/**
	 * 属性
	 */
	private OptionalItemAtr optionalItemAtr;
	
	/**
	 * 単位
	 */
	private UnitOfOptionalItem unit;
}
