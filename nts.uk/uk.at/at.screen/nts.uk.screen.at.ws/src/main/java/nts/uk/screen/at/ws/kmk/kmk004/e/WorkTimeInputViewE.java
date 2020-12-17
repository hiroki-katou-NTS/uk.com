package nts.uk.screen.at.ws.kmk.kmk004.e;

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
public class WorkTimeInputViewE {

	public String sid;

	public List<Integer> yearMonth;

	public List<Integer> laborTime;

}
