package nts.uk.ctx.at.record.infra.repository.log;

import lombok.Value;

@Value
public class PersonError {
	String disposalDay;
	String empCalAndSumExecLogID;
	String messageError;
	String resourceID;
	String employeeID;
	int executionContent;
	String personCode;
	String personName;
}
