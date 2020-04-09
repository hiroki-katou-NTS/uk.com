package nts.uk.ctx.at.record.app.command.stamp.card.stampcard.management.personalengraving;

import java.util.Optional;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * @author anhdt
 *
 */
@Data
public class RegisterStampDataResult {
	
	private String reflectDate;
	private String employeeID;
	
	public RegisterStampDataResult(String employeeId, Optional<GeneralDate> reflectDate) {
		this.employeeID = employeeId;
		this.reflectDate = reflectDate.isPresent() ? reflectDate.get().toString() : null;
	}
}
