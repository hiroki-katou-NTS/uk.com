package nts.uk.screen.at.app.query.ksu.ksu002.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ListOfPeriodsCloseInput {

	public String sid;

	public YearMonth baseDate;

}
