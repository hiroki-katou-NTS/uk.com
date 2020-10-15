/**
 * 
 */
package nts.uk.screen.at.app.ksu001.eventinformationandpersonal;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * @author laitv
 *
 */
@Value
public class EventInfoAndPerCondPeriodParam {
	public GeneralDate startDate;
	public GeneralDate endDate;
	public List<String> listSid;
	public TargetOrgIdenInfor targetOrgIdenInfor;
}
