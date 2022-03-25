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
public class GetWorkLocationAndRegionalTimeDifferenceInput {

	// 契約コード
	private String contractCode;

	// 勤務場所コード
	private String workLocationCode;
	
	// Ipv4Address
	private String ipv4Address;
}
