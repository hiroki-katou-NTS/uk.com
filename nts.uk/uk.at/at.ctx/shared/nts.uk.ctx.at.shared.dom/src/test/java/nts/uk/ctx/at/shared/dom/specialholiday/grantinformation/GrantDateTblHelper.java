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
public class GrantDateTblHelper {

	public static GrantDateTbl createGrantDateTbl() {
		
		List<GrantElapseYearMonth> elapseYear = new ArrayList<GrantElapseYearMonth>();
		GrantElapseYearMonth item1 = GrantElapseYearMonth.createFromJavaType(1, 6);
		elapseYear.add(item1);
		
		 return new GrantDateTbl(
				 "companyId",
				 new SpecialHolidayCode(1),
				 new GrantDateCode("01"),
				 new GrantDateName("grantdatename"),
				 elapseYear,
				 true,
				 Optional.ofNullable(new GrantedDays(6)));
	}
	
	public static GrantDateTbl createGrantDateTbl1() {
		
		List<GrantElapseYearMonth> elapseYear = new ArrayList<GrantElapseYearMonth>();
		
		GrantElapseYearMonth item1 = GrantElapseYearMonth.createFromJavaType(1, 6);
		GrantElapseYearMonth item2 = GrantElapseYearMonth.createFromJavaType(2, 3);
		GrantElapseYearMonth item3 = GrantElapseYearMonth.createFromJavaType(3, 3);
		GrantElapseYearMonth item4 = GrantElapseYearMonth.createFromJavaType(4, 5);
		
		elapseYear.add(item1);
		elapseYear.add(item2);
		elapseYear.add(item3);
		elapseYear.add(item4);
		
		 return new GrantDateTbl(
				 "companyId",
				 new SpecialHolidayCode(1),
				 new GrantDateCode("01"),
				 new GrantDateName("grantdatename"),
				 elapseYear,
				 true,
				 Optional.ofNullable(new GrantedDays(6)));
	}
		
	// isSpecified() = false
	public static GrantDateTbl createGrantDateTbl3() {
		 return new GrantDateTbl(
				 "companyId",
				 new SpecialHolidayCode(1),
				 new GrantDateCode("01"),
				 new GrantDateName("grantdatename"),
				 new ArrayList<GrantElapseYearMonth>(),
				 false,
				 Optional.ofNullable(new GrantedDays(6)));
	}
	
	public static GrantDateTbl createGrantDateTbl4() {
		 return new GrantDateTbl(
				 "companyId",
				 new SpecialHolidayCode(1),
				 new GrantDateCode("03"),
				 new GrantDateName("grantdatename3"),
				 new ArrayList<GrantElapseYearMonth>(),
				 true,
				 Optional.ofNullable(new GrantedDays(6)));
	}
}
