package nts.uk.ctx.at.record.dom.optitem.event;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.event.DomainEvent;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;

/**
 * 任意項目の属性が更新された
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class OptionalItemAtrDomainEvent extends DomainEvent {
	/**
	 * 実績区分
	 */
	private PerformanceAtr performanceAtr;

	/**
	 * 任意項目の属性
	 */
	private OptionalItemAtr optionalItemAtr;

	/**
	 * 任意項目NO
	 */
	private OptionalItemNo optionalItemNo;

}
