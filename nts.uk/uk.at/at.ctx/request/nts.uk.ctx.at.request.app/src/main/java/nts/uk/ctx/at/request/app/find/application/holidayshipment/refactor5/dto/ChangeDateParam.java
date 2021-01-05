package nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@Setter
public class ChangeDateParam {

	public String workingDate;
	
	public String holidayDate;
	@Getter
	public DisplayInforWhenStarting displayInforWhenStarting;

	public Optional<GeneralDate> getWorkingDate() {
		return workingDate == null ? Optional.empty(): Optional.of(GeneralDate.fromString(workingDate, "yyyy/MM/dd"));
	}

	public Optional<GeneralDate> getHolidayDate() {
		return holidayDate == null ? Optional.empty(): Optional.of(GeneralDate.fromString(holidayDate, "yyyy/MM/dd"));
	}
	
}
