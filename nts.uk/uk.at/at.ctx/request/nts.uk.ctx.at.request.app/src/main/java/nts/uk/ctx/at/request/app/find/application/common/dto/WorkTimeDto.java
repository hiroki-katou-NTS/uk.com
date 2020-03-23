package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.WorkTimeOutput;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class WorkTimeDto {
	
	private String workTimeCD;
	
	private String workTimeName;
	
	public static WorkTimeDto convertFromWorkTimeOutput(WorkTimeOutput workTimeOutput){
		return new WorkTimeDto(workTimeOutput.getWorkTimeCD(), workTimeOutput.getWorkTimeName());
	}
	
	public WorkTimeOutput toDomain() {
		return new WorkTimeOutput(workTimeCD, workTimeName);
	}
}
