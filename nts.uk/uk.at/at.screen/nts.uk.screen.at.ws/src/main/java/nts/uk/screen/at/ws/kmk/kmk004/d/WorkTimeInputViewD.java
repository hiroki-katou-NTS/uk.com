package nts.uk.screen.at.ws.kmk.kmk004.d;

import java.util.List;

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
public class WorkTimeInputViewD {

	public String employmentCode;
	
	public List<Integer> yearMonth;
	
	public List<Integer> laborTime;
	
}
