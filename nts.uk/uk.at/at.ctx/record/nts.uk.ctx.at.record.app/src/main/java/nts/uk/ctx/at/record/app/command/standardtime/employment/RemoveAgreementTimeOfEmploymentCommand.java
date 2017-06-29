package nts.uk.ctx.at.record.app.command.standardtime.employment;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class RemoveAgreementTimeOfEmploymentCommand {

	private int laborSystemAtr;
	
	private String employmentCategoryCode;
}
