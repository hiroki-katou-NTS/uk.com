/**
 * 4:16:42 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
/**
 * @author hungnm
 *
 */
@Getter
public class CompanyEvent extends AggregateRoot {

	private String companyId;

	private BigDecimal date;

	private EventName eventName;

	private CompanyEvent(String companyId, BigDecimal date, EventName eventName) {
		super();
		this.companyId = companyId;
		this.date = date;
		this.eventName = eventName;
	}

	private CompanyEvent() {
		super();
	}

	public static CompanyEvent createFromJavaType(String companyId, BigDecimal date, String eventName) {
		return new CompanyEvent(companyId, date, new EventName(eventName));
	}

}
