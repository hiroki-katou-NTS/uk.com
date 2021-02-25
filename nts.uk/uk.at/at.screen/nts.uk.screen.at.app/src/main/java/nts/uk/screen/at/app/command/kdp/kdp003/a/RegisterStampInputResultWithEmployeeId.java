package nts.uk.screen.at.app.command.kdp.kdp003.a;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.command.kdp.kdp001.a.RegisterStampInputResult;


@Setter
@Getter
public class RegisterStampInputResultWithEmployeeId extends RegisterStampInputResult {

	public RegisterStampInputResultWithEmployeeId(GeneralDate reflectDate) {
		super(reflectDate);
	}
}
