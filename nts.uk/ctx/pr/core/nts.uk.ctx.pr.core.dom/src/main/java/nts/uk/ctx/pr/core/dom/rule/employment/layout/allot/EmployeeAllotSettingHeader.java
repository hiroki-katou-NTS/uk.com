package nts.uk.ctx.pr.core.dom.rule.employment.layout.allot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;

@Getter
@AllArgsConstructor
public class EmployeeAllotSettingHeader extends AggregateRoot{
	
	private  CompanyCode companyCode;
	
	private  YearMonth startDate;
	
	private YearMonth endDate;
	
	private String historyId;
	 
	/**
	 * 
	 */
	public static EmployeeAllotSettingHeader createFromJavaType(String companyCode, int startYearMonth, int endYearMonth, String historyId) {
		return new EmployeeAllotSettingHeader(
					new CompanyCode(companyCode), 
					new YearMonth(startYearMonth), 
					new YearMonth(endYearMonth), 
					historyId);
	}
 }

