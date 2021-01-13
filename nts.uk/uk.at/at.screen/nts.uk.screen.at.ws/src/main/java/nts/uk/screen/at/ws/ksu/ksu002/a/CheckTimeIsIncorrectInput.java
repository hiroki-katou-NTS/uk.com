package nts.uk.screen.at.ws.ksu.ksu002.a;

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
public class CheckTimeIsIncorrectInput {

	public String workType;
	public String workTime;
	public Integer startTime;
	public Integer endTime;
}
