package nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * Child nursing leave request
 *
 */
@Data
public class ChildNursingLeaveRequest {
	
	private List<String> lstEmployees;
	
	private GeneralDate baseDate;

}
