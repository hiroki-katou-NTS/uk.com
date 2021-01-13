package nts.uk.screen.at.app.query.ksu.ksu002.a.dto;

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
