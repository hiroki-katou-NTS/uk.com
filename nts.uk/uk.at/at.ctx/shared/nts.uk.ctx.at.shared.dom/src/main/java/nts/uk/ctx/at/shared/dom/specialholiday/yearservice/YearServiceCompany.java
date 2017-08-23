package nts.uk.ctx.at.shared.dom.specialholiday.yearservice;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

@Getter
public class YearServiceCompany {

	/*特別休暇コード*/
	private SpecialHolidayCode specialHolidayCode;
	
	/*続柄コード*/
	private String yearDateId;
	
	/*続柄に対する付与日数*/
	private ServiceGrantDay serviceGrantDay;
	
	/*特別休暇付与日数*/
	private LengthServiceYearAtr lengthServiceYearAtr;
	

}
