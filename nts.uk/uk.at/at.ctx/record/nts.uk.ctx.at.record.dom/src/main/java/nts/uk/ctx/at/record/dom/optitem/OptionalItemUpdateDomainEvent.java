package nts.uk.ctx.at.record.dom.optitem;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.event.DomainEvent;

/**
 * 任意項目の属性が更新された
 * 
 * @author NWS-HoangNM
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class OptionalItemUpdateDomainEvent extends DomainEvent {
	/** The optional item no. */
	// 任意項目NO
	private OptionalItemNo optionalItemNo;
	
	/** The optional item atr. */
	// 任意項目の属性 
	private OptionalItemAtr optionalItemAtr;
	
	/** The performance atr. */
	// 実績区分
	private PerformanceAtr performanceAtr;
}
