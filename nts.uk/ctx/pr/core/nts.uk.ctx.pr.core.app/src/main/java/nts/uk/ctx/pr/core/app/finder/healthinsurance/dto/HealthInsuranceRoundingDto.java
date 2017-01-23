package nts.uk.ctx.pr.core.app.finder.healthinsurance.dto;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.RoundingItem;

@Builder
@Data
public class HealthInsuranceRoundingDto {
	/** The pay type. */
	private PaymentType payType;

	/** The round atrs. */
	private RoundingItem roundAtrs;
}
