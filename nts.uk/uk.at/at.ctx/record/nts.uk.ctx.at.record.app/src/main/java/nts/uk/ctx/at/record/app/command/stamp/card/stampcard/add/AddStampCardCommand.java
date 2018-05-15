package nts.uk.ctx.at.record.app.command.stamp.card.stampcard.add;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AddStampCardCommand {

	
	/**社員ID*/
	@PeregEmployeeId
	private String employeeId;
	
	@PeregItem("IS00779")
	private String stampNumber;

}
