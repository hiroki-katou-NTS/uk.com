/**
 * 10:00:44 AM Jun 30, 2017
 */
package nts.uk.ctx.at.schedule.app.find.event;

import java.math.BigDecimal;
import java.util.List;

import lombok.Value;

/**
 * @author hungnm
 *
 */
@Value
public class WkpRequest {
	private String workplaceId;
	private List<BigDecimal> lstDate;
}
