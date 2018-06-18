package nts.uk.file.at.app.export.dailyschedule;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * Instantiates a new work schedule output query.
 * @author HoangNDH
 */
@Data
public class WorkScheduleOutputQuery implements Cloneable {
	
	/** The start date. */
	private GeneralDate startDate;
	
	/** The end date. */
	private GeneralDate endDate;
	
	/** The employee id. */
	private List<String> employeeId;
	
	/** The condition. */
	private WorkScheduleOutputCondition condition;
	
	/** The file type. */
	private FileOutputType fileType;
	
	/**
	 * Creates the from java type.
	 *
	 * @param dto the dto
	 * @return the work schedule output query
	 */
	public static WorkScheduleOutputQuery createFromJavaType(WorkScheduleOutputQueryDto dto) {
		WorkScheduleOutputQuery query = new WorkScheduleOutputQuery();
		query.setStartDate(dto.getStartDate());
		query.setEndDate(dto.getEndDate());
		query.setEmployeeId(dto.getLstEmployeeId());
		query.setFileType(FileOutputType.valueOf(dto.getFileType()));
		query.setCondition(WorkScheduleOutputCondition.createFromJavaType(dto.getCondition()));
		return query;
	}
}
