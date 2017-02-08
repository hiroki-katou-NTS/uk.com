package nts.uk.ctx.core.app.insurance.social.pensionrate.find;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.RoundingItem;

@Data
public class PensionRateRoundingDto {

	/** The pay type. */
	private PaymentType payType;

	/** The round atrs. */
	private RoundingItem roundAtrs;
}
