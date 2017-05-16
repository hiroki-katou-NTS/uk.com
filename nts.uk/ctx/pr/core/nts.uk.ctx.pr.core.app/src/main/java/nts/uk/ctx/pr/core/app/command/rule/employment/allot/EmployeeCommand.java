package nts.uk.ctx.pr.core.app.command.rule.employment.allot;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.EmployeeAllotSetting;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
public class EmployeeCommand {

	private String employmentCode;

	private String paymentDetailCode;

	private String bonusDetailCode;

	public EmployeeAllotSetting toDomain(String historyId) {
		String companyCode = AppContexts.user().companyCode();
		return EmployeeAllotSetting.createFromJavaType(companyCode, historyId, this.getEmploymentCode(),
				this.getPaymentDetailCode(), this.getBonusDetailCode());
	}
}
