package nts.uk.ctx.at.record.app.find.log.dto;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author hieult
 *
 */
@Value
public class ScreenImplementationResultDto {

	private String empCalAndSumExecLogID;

	private int executionContent;
	
	private List<String> employeeID;

}