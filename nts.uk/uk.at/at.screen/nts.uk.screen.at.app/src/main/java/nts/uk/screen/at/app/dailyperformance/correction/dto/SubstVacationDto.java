/**
 * 11:26:05 AM Aug 22, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hungnm - 振休残数
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubstVacationDto {

	private boolean manageAtr;

	private Double holidayRemain;
	
}
