package nts.uk.ctx.pr.core.app.command.rule.employment.allot;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.CompanyAllotSetting;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
public class DelAllotCompanyCmd {
	
	private String historyId;

	private int startDate;

	private int endDate;

	private String payStmtCode;

	private String bonusStmtCode;

	public CompanyAllotSetting toDomain() {
		String companyCode = AppContexts.user().companyCode();

		return CompanyAllotSetting.createFromJavaType(companyCode, this.getHistoryId(), this.getStartDate(),
				this.getEndDate(), this.getPayStmtCode(), this.getBonusStmtCode());
	}

}
