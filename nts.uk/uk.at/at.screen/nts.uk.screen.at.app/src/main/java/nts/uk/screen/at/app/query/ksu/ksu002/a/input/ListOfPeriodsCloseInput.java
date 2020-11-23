package nts.uk.screen.at.app.query.ksu.ksu002.a.input;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author chungnt
 *
 */

@NoArgsConstructor
@AllArgsConstructor
public class ListOfPeriodsCloseInput {
	@Setter
	public Integer yearMonth;
	
	public int getYearMonth() {
		return this.yearMonth == null ? -1 : this.yearMonth.intValue();
	}
}
