package nts.uk.screen.at.app.kdl045.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Map<時間休暇種類, 時間休暇> dto
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeVacationAndType {
	/**
	 * 時間休暇種類 
	 */
	private int typeVacation;
	
	/**
	 * 時間休暇 
	 */
	private TimeVacationDto timeVacation;
}
