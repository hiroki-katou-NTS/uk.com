package nts.uk.screen.at.ws.kmk.kmk004.c;

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
public class InputDeleteWorkTimeViewC {

	public String workplaceId;
	
	public int startMonth;
	
	public int endMonth;
	
}
