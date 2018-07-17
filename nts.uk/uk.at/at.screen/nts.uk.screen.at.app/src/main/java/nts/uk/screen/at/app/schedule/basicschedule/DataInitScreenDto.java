package nts.uk.screen.at.app.schedule.basicschedule;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

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
	// get symbol
	List<WorkEmpCombineScreenDto> workEmpCombines;
	String employeeIdLogin;
}
