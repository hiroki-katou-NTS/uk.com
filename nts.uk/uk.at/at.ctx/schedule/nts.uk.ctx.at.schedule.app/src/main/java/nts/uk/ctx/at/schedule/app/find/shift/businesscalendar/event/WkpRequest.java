/**
 * 10:00:44 AM Jun 30, 2017
 */
package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.event;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
@Getter
@Setter
public class WkpRequest {
	
	private String workplaceId;
	
	private List<GeneralDate> lstDate;
	
	private WkpRequest(String workplaceId, List<GeneralDate> lstDate) {
		super();
		this.workplaceId = workplaceId;
		this.lstDate = lstDate;
	}
	private WkpRequest() {
		super();
	}
	
	
}
