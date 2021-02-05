package nts.uk.screen.at.ws.kmk.kmk004.c;

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
public class WorkTimeInputViewC {

	public String workPlaceId;
	
	public List<Integer> yearMonth;
	
	public List<Integer> laborTime;
	
}
