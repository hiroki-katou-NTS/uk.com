package nts.uk.screen.at.ws.schedule.basicschedule;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.schedule.basicschedule.StateWorkTypeCodeDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkEmpCombineScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkTimeScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkTypeScreenDto;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class DataInitScreenDto {
	List<WorkTypeScreenDto> listWorkType;
	List<WorkTimeScreenDto> listWorkTime;
	GeneralDate startDate;
	GeneralDate endDate;
	List<StateWorkTypeCodeDto> checkStateWorkTypeCode;
	List<StateWorkTypeCodeDto> checkNeededOfWorkTimeSetting;
	List<WorkEmpCombineScreenDto> workEmpCombines;
	String employeeIdLogin;
}
