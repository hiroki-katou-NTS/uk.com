package nts.uk.file.at.app.export.dailyschedule;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;


/**
 * Instantiates a new work schedule output query dto.
 * @author HoangNDH
 */
@Data
public class WorkScheduleOutputQueryDto {
	
	/** The start date. */
	private GeneralDate startDate;
	
	/** The end date. */
	private GeneralDate endDate;
	
	/** The lst employee id. */
	private List<String> lstEmployeeId;
	
	/** The file type. */
	private int fileType;
	
	/** The work schedule output condition dto. */
	private WorkScheduleOutputConditionDto condition;

	private GeneralDate baseDate;
}
