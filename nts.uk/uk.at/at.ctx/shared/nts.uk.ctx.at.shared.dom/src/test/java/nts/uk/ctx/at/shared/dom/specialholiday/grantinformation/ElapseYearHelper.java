package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

/**
 * 
 * @author dungbn
 *
 */
public class ElapseYearHelper {

	public static ElapseYear createElapseYear() {
		return new ElapseYear(
				"companyId",
				new SpecialHolidayCode(1),
				new ArrayList<ElapseYearMonthTbl>(),
				true,
				Optional.ofNullable(GrantCycleAfterTbl.createFromJavaType(2, 2)));
	}
	
	public static ElapseYear createElapseYear1() {
		
		List<ElapseYearMonthTbl> elapseYearMonthTblList = new ArrayList<ElapseYearMonthTbl>();
		
		for (int i = 1; i <= 3; i++) {
			ElapseYearMonthTbl elapseYearMonthTbl = ElapseYearMonthTbl.createFromJavaType(i, i, i);
			elapseYearMonthTblList.add(elapseYearMonthTbl);
		}
		
		return new ElapseYear(
				"companyId",
				new SpecialHolidayCode(1),
				elapseYearMonthTblList,
				true,
				Optional.ofNullable(GrantCycleAfterTbl.createFromJavaType(2, 2)));
	}
	
	
}
