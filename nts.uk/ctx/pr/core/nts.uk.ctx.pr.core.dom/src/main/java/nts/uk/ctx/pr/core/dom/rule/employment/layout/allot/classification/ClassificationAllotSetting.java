package nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.paymentdata.BonusDetailCode;
import nts.uk.ctx.pr.core.dom.paymentdata.PaymentDetailCode;
@AllArgsConstructor
public class ClassificationAllotSetting extends AggregateRoot {

	@Getter
	private final CompanyCode companyCode;

	@Getter
	private String histotyId;

	@Getter
	private ClassificationCode classificationCode;

	@Getter
	private PaymentDetailCode paymentDetailCode;

	@Getter
	private BonusDetailCode bonusDetailCode;


	public static ClassificationAllotSetting createFromJavaType(String companyCode, String historyId,
			String classificationCode, String bonusDetailCode, String paymentDetailCode) {
		return new ClassificationAllotSetting(new CompanyCode(companyCode), historyId,
				new ClassificationCode(classificationCode), new PaymentDetailCode(paymentDetailCode),
				new BonusDetailCode(bonusDetailCode));
	}

}
