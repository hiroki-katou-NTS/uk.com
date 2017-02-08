package nts.uk.ctx.core.app.insurance.social.find.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialInsuranceOfficeItemDto {
	/** The company code. */
	private String companyCode;

	/** The code. */
	private String code;

	/** The name. */
	private String name;
}
