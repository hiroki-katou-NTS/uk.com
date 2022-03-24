package nts.uk.screen.at.app.cmm040.worklocation;

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
public class GetInfoOnTimeDifferenceDto {

	// コード
	private int code;

	// 名称
	private String name;

	// 時差
	private int regionalTimeDifference;

}
