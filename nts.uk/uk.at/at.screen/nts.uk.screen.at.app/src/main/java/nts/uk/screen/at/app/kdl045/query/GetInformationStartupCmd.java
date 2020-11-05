package nts.uk.screen.at.app.kdl045.query;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.TargetOrgIdenInforDto;

/**
 * 
 * @author tutk
 *
 */
@Data
public class GetInformationStartupCmd {
	private String employeeId; 
	private String baseDate;
	private List<TimeVacationAndType> listTimeVacationAndType; 
	private String workTimeCode;
	private TargetOrgIdenInforDto targetOrgIdenInforDto;

}
