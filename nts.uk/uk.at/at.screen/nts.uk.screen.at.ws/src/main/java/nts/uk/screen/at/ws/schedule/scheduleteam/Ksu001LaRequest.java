package nts.uk.screen.at.ws.schedule.scheduleteam;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class Ksu001LaRequest {
	private String baseDate;
	private String WKPGRID;

	public GeneralDate toDate() {
		return GeneralDate.fromString(baseDate, "yyyy/MM/dd");
	}
}
