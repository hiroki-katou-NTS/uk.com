package nts.uk.screen.at.app.query.kdp.kdp002.a;

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
public class CreateDailyParam {

	public String sid;
	
	public GeneralDate date;
	
}
