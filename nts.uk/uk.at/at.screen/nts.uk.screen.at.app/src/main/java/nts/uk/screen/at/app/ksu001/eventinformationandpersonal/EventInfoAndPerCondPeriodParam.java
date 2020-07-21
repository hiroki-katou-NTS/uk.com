/**
 * 
 */
package nts.uk.screen.at.app.ksu001.eventinformationandpersonal;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * @author laitv
 *
 */
@Data
public class EventInfoAndPerCondPeriodParam {
	public GeneralDate startDate;
	public GeneralDate endDate;
	public String workplaceId;
	public String workplaceGroupId;
	public List<String> listSid;
}
