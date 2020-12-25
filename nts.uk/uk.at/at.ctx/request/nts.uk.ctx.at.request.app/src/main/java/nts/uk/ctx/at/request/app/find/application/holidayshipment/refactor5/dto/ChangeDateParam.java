package nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@Getter
@Setter
public class ChangeDateParam {

	public GeneralDate workingDate;
	
	public GeneralDate holidayDate;
	
	public DisplayInforWhenStarting displayInforWhenStarting;
	
}
