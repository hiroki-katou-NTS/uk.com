package nts.uk.screen.at.app.stamp.personalengraving.query;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author anhdt
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StampResultConfirmRequest {
	private String stampDate;
	private List<Integer> attendanceItems;
	
	public DatePeriod toStampDatePeriod() {
		return new DatePeriod(GeneralDate.today(), GeneralDate.today());
	}
	
	public boolean isContain28() {
		return attendanceItems.stream().filter(e -> e == 28).findAny().isPresent();
	}
	
	public boolean isContain29() {
		return attendanceItems.stream().filter(e -> e == 29).findAny().isPresent();
	}
	
	public boolean isContain31() {
		return attendanceItems.stream().filter(e -> e == 31).findAny().isPresent();
	}
	
	public boolean isContain34() {
		return attendanceItems.stream().filter(e -> e == 34).findAny().isPresent();
	}
	
	public void correctRequest() {
		if(!this.isContain28()) {
			this.attendanceItems.add(28);						
		}
		if(!this.isContain29()) {
			this.attendanceItems.add(29);		
		}
		if(!this.isContain31()) {
			this.attendanceItems.add(31);
		}
		if(!this.isContain34()) {
			this.attendanceItems.add(34);
		}
	}
}
