/**
 * 4:16:42 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.event;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author hungnm
 *
 */
@Getter
public class CompanyEvent extends AggregateRoot {

	CompanyId companyId;

	GeneralDate date;

	EventName eventName;

	private CompanyEvent(CompanyId companyId, GeneralDate date, EventName eventName) {
		super();
		this.companyId = companyId;
		this.date = date;
		this.eventName = eventName;
	}

	private CompanyEvent() {
		super();
	}

	public static CompanyEvent createFromJavaType(String companyId, GeneralDate date, String eventName) {
		return new CompanyEvent(new CompanyId(companyId), date, new EventName(eventName));
	}

}
