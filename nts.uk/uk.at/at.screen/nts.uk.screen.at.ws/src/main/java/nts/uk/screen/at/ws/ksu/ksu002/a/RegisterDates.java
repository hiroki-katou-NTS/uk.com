package nts.uk.screen.at.ws.ksu.ksu002.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDates {

	public GeneralDate date;
	public String workTypeCd;
	public String workTimeCd;
	public int start;
	public int end;
	
}
