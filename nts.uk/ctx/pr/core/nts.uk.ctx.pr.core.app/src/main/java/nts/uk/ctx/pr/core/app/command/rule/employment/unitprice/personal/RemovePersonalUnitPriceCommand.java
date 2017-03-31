package nts.uk.ctx.pr.core.app.command.rule.employment.unitprice.personal;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnh
 *
 */
@NoArgsConstructor
@Data
public class RemovePersonalUnitPriceCommand {
	private String personalUnitPriceCode;
}
