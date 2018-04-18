package nts.uk.ctx.pereg.ac.common;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.shared.dom.specialholiday.event.SpecialHolidayEvent;

/**
 * Event:特休項目の名称変更と廃止区分の切り替え
 * @author xuanv
 *
 */

public class SpecGlobalEventSubscriber implements DomainEventSubscriber<SpecialHolidayEvent>{

	@Override
	public Class<SpecialHolidayEvent> subscribedToEventType() {
		return SpecialHolidayEvent.class;
	}

	@Override
	public void handle(SpecialHolidayEvent domainEvent) {
		
	}

}
