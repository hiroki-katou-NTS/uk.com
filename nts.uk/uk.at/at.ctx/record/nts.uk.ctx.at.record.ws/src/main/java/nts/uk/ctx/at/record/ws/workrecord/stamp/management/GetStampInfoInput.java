package nts.uk.ctx.at.record.ws.workrecord.stamp.management;

import lombok.Data;

@Data
public class GetStampInfoInput {

	private String employeeId;
	
	private int regionalTimeDifference;
	
}
