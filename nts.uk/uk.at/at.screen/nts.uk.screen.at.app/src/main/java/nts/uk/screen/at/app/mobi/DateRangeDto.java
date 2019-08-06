package nts.uk.screen.at.app.mobi;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class DateRangeDto {
	private GeneralDate start;
	private GeneralDate end;
}
