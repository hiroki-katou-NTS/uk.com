package nts.uk.ctx.core.app.insurance.labor;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LaborInsuranceOfficeInDto {

	/** The company code. */
	private String companyCode;
	/** The code. officeCode */
	private String code;
	/** The name. officeName */
	private String name;

}
