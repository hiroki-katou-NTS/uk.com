package nts.uk.ctx.pr.core.app.find.insurance.social.dto;

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
