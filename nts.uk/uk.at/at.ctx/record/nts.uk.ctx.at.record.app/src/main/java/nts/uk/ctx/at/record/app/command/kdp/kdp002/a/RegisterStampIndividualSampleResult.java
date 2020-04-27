package nts.uk.ctx.at.record.app.command.kdp.kdp002.a;

import java.util.Optional;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author lamvt
 *
 */
@Data
public class RegisterStampIndividualSampleResult {
	
	private String reflectDate;
	
	public RegisterStampIndividualSampleResult(Optional<GeneralDate> reflectDate) {
		this.reflectDate = reflectDate.isPresent() ? reflectDate.get().toString() : null;
	}
}
