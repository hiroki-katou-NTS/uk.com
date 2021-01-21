/**
 * 
 */
package nts.uk.screen.at.app.ksu001.validwhenedittime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author laitv
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ValidDataWhenEditTimeParam {

	public String workTypeCode;
	public String workTimeCode;
	public Integer startTime;
	public Integer endTime;
}
