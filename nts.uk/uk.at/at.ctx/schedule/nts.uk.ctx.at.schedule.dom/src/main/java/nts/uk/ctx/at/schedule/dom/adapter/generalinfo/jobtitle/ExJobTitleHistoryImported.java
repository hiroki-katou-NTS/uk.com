package nts.uk.ctx.at.schedule.dom.adapter.generalinfo.jobtitle;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Data
public class ExJobTitleHistoryImported {

	private String employeeId;

	private List<ExJobTitleHistItemImported> jobTitleItems;

}
