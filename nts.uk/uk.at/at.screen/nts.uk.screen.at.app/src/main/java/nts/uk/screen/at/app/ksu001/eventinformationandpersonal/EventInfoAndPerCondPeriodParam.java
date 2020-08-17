/**
 * 
 */
package nts.uk.screen.at.app.ksu001.eventinformationandpersonal;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * @author laitv
 *
 */
@Value
public class EventInfoAndPerCondPeriodParam {
	public GeneralDate startDate;
	public GeneralDate endDate;
	public String workplaceId;
	public String workplaceGroupId;
	public List<String> listSid;
}
