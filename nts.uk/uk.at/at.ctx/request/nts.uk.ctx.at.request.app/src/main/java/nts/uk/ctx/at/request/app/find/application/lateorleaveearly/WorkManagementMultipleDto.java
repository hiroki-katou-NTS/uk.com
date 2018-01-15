package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;

@Value
public class WorkManagementMultipleDto {
	/** 0: 使用しない
 	  * 1: 使用する 
 	  * */
	int useATR;

	public static WorkManagementMultipleDto convertoDto(WorkManagementMultiple workManagementMultiple) {
		if (workManagementMultiple == null)
			return null;

		return new WorkManagementMultipleDto(workManagementMultiple.getUseATR().value);
	}

}
