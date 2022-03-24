package nts.uk.screen.at.app.query.kdp.common;

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
public class GetWorkLocationAndRegionalTimeDifferenceDto {

	// 職場ID
	private String workPlaceId;

	// 地域時差
	private int regionalTime = 0;

}
