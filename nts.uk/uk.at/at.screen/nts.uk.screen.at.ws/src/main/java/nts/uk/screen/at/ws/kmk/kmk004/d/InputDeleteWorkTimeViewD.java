package nts.uk.screen.at.ws.kmk.kmk004.d;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InputDeleteWorkTimeViewD {

	public String employmentCode;
	
	public int startMonth;
	
	public int endMonth;
	
}
