package nts.uk.screen.at.app.query.kmp.kmp001.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StampCardDigitDto {

	private int stampCardDigitNumber;
	private int stampCardEditMethod;
	private boolean isIc_card = false;
}
