package nts.uk.screen.at.ws.kmk004;

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
@Data
@NoArgsConstructor
public class WorkTimeInputViewB {
	public List<Integer> yearMonth;

	public List<Integer> laborTime;
}
