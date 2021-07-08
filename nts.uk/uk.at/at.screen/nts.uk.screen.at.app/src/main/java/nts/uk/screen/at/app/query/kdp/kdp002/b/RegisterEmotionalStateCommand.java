package nts.uk.screen.at.app.query.kdp.kdp002.b;

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
public class RegisterEmotionalStateCommand {

	public String sid;
	
	public int emoji;
	
	public GeneralDate date;
	
}
