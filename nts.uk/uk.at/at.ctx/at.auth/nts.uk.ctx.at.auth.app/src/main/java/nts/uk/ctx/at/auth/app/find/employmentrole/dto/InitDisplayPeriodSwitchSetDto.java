/**
 * 
 */
package nts.uk.ctx.at.auth.app.find.employmentrole.dto;

import java.util.List;

import lombok.Data;
import lombok.Value;

/**
 * @author hieult
 *
 */
@Value
@Data
public class InitDisplayPeriodSwitchSetDto {

	private int currentOrNextMonth;
	private List<DateProcessed> listDateProcessed;

}
