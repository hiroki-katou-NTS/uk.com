package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@Getter
public class SPRStampSourceInfo extends EmpAndDate{
	
	@Setter
	private boolean change31;
	
	@Setter
	private boolean change34;

	public SPRStampSourceInfo(String employeeId, GeneralDate date, boolean change31, boolean change34) {
		super(employeeId, date);
		this.change31 = change31;
		this.change34 = change34;
	}
   
}
