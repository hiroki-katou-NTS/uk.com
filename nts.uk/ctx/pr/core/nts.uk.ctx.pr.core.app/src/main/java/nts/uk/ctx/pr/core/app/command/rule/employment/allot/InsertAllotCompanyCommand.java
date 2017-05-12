package nts.uk.ctx.pr.core.app.command.rule.employment.allot;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.CompanyAllotSetting;
import nts.uk.shr.com.context.AppContexts;

@Setter
@Getter
public class InsertAllotCompanyCommand {
	private String historyId;
	private int startDate;
	private int endDate;
	private String payStmtCode;
	private String bonusStmtCode;

	// Convert to domain object from command values
	public CompanyAllotSetting toDomain(String historyId) {
		String companyCode = AppContexts.user().companyCode();
		return CompanyAllotSetting.createFromJavaType(companyCode, historyId, getStartDate(),
				getEndDate(), getPayStmtCode(), getBonusStmtCode());
	}
}
