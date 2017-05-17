package nts.uk.ctx.pr.core.app.command.rule.employment.allot;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.employee.EmployeeAllotSettingHeader;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
public class DeleteEmployeeAllotHeaderCommand {
	private String historyId;

	private int startYm;

	private int endYm;

	private List<EmployeeCommand> employees;

	public EmployeeAllotSettingHeader toDomain() {
		String companyCode = AppContexts.user().companyCode();

		return EmployeeAllotSettingHeader.createFromJavaType(companyCode, this.getHistoryId(), this.getStartYm(),
				this.getEndYm());
	}
}
