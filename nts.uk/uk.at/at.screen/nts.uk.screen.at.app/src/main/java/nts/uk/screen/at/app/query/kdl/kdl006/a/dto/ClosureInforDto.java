package nts.uk.screen.at.app.query.kdl.kdl006.a.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
@Data
@NoArgsConstructor
public class ClosureInforDto {

	public Integer closureId;
	
	public Integer yearMonth;
	
	public String closureName;
	
	public GeneralDate start;
	
	public GeneralDate end;

	public ClosureInforDto(Integer closureId, Integer yearMonth, String closureName, DatePeriod datePeriod) {
		super();
		this.closureId = closureId;
		this.yearMonth = yearMonth;
		this.closureName = closureName;
		this.start = datePeriod.start();
		this.end = datePeriod.end();
	}
}
