/**
 * 2:10:58 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.app.find.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.event.CompanyEvent;

/**
 * @author hungnm
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyEventDto {

	private int date;

	private String name;

	public static CompanyEventDto fromDomain(CompanyEvent domain) {
		return new CompanyEventDto(domain.getDate().intValue(), domain.getEventName().v());
	}

}
