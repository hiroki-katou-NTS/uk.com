package nts.uk.ctx.at.shared.dom.specialholiday.yearservice;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class YearServiceSet {
	
	/*会社ID*/
	private String companyId;

	/*勤続年数基ID*/
	private String yearServiceId;

	/*勤続年数基月数*/
	private YearServiceMonths yearServiceMonths;

	/*勤続年数基年数*/
	private YearServiceYears yearServiceYears;

	/*特別休暇付与日数*/
	private GrantDates grantDates;

	public static YearServiceSet createSimpleFromJavaType(String companyId,
			String yearServiceId,
			int yearServiceMonths,
			int yearServiceYears, 
			int grantDates){
					return new YearServiceSet(companyId, 
							yearServiceId,
							new YearServiceMonths(yearServiceMonths),
							new YearServiceYears(yearServiceYears),
							new GrantDates(grantDates));
	}
}
