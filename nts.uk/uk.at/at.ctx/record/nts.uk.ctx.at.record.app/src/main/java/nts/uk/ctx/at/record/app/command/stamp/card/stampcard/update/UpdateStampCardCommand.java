package nts.uk.ctx.at.record.app.command.stamp.card.stampcard.update;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class UpdateStampCardCommand {

	
	@PeregRecordId
	private String stampNumberId;
	
	/**社員ID*/
	@PeregEmployeeId
	private String employeeId;
	
	@PeregItem("IS00779")
	private String stampNumber;

}
