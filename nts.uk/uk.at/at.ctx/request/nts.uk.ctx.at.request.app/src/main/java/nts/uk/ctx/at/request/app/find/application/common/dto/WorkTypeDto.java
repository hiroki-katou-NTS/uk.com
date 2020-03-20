package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.WorkTypeOutput;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class WorkTypeDto {
	
	/** The work type code. */
	/* 勤務種類コード */
	private String workTypeCode;

	/** The name. */
	/* 勤務種類名称 */
	private String name;
	
	public static WorkTypeDto convertFromWorkTypeOutput(WorkTypeOutput workTypeOutput){
		return new WorkTypeDto(workTypeOutput.getWorkTypeCode(), workTypeOutput.getName());
	} 
	
	public WorkTypeOutput toDomain() {
		return new WorkTypeOutput(workTypeCode, name);
	}
	
}
