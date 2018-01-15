/**
 * 
 */
package nts.uk.ctx.at.record.app.command.workrecord.worktype;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.record.app.find.workrecord.worktype.WorkTypeGroupDto;

/**
 * @author danpv
 *
 */
@Data
public class WorkTypeEmploymentCommand {
	
	private String employmentCode;
	
	private List<WorkTypeGroupDto> groups;

}
