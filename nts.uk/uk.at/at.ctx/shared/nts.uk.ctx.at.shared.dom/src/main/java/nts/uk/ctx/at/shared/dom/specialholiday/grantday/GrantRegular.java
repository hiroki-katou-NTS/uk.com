package nts.uk.ctx.at.shared.dom.specialholiday.grantday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GrantRegular {

	/*会社ID*/
	private String companyId;
	
	/*特別休暇コード*/
	private SpecialHolidayCode specialHolidayCode;
	
	/*付与開始日*/
	private GeneralDate grantStartDate;
	
	/*月数*/
	private Months months;
	
	/*年数*/
	private Years years;
	
	/*付与日定期方法*/
	private GrantRegularMethod grantRegularMethod;

	public static GrantRegular createFromJavaType(String companyId,
			int specialHolidayCode,
			GeneralDate grantStartDate,
			int months,
			int years,
			int grantRegularMethod){
					return new GrantRegular(companyId,
							new SpecialHolidayCode(specialHolidayCode),
							grantStartDate ,
							new Months(months),
							new Years(years),
							EnumAdaptor.valueOf(grantRegularMethod, GrantRegularMethod.class));
	}	
}
