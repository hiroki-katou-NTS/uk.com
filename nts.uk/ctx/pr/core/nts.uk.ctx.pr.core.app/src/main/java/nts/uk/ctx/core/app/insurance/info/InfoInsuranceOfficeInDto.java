package nts.uk.ctx.core.app.insurance.info;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InfoInsuranceOfficeInDto {
	/** The company code. */
	private String companyCode;
	/** The code. officeCode */
	private String code;
	/** The name. officeName */
	private String name;

}
