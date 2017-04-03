package nts.uk.ctx.pr.core.app.find.rule.employment.allot;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.EmployeeAllotSettingHeader;

@Value
public class EmployeeAllotSettingHeaderDto {
	String companyCode;
	int startDate;
	int endDate;
	String historyId;
	
	public static EmployeeAllotSettingHeaderDto fromDomain(EmployeeAllotSettingHeader EmployeeAllotSettingHeader) {
		return new EmployeeAllotSettingHeaderDto(
				EmployeeAllotSettingHeader.getCompanyCode().v(),
				EmployeeAllotSettingHeader.getStartDate().v(),
				EmployeeAllotSettingHeader.getEndDate().v(),
				EmployeeAllotSettingHeader.getHistoryId());
	}
}
