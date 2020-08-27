package nts.uk.screen.at.app.shiftmanagement.shifttable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ksu001uRequest {
	private int unit;
	private String workplaceId;
	private String workplaceGroupId;
	private String baseDate;
	
	public GeneralDate toDate() {
		return GeneralDate.fromString(baseDate, "yyyy/MM/dd");
	}

}
