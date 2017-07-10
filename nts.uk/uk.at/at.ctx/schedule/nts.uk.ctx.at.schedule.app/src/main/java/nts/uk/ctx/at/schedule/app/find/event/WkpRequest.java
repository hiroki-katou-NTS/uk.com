/**
 * 10:00:44 AM Jun 30, 2017
 */
package nts.uk.ctx.at.schedule.app.find.event;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hungnm
 *
 */
@Getter
@Setter
public class WkpRequest {
	
	private String workplaceId;
	
	private List<BigDecimal> lstDate;
	
	private WkpRequest(String workplaceId, List<BigDecimal> lstDate) {
		super();
		this.workplaceId = workplaceId;
		this.lstDate = lstDate;
	}
	private WkpRequest() {
		super();
	}
	
	
}
