package nts.uk.screen.at.app.kdw013.a;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@Getter
public class WorkDetailsParamCommand {
	// 応援勤務枠No: 応援勤務枠No
	private int supportFrameNo;
	
	// 時間帯: 時間帯
	private TimeZoneCommand timeZone;

}
