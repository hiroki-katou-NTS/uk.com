package nts.uk.ctx.at.shared.dom.specialholiday.yearserviceper;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;

@Getter
@AllArgsConstructor
public class YearServicePerSet {
	/**会社ID**/
	private String companyId;
	
	private String specialHolidayCode;
	/**コード**/
	private String yearServiceCode;
	private int yearServiceNo;
	private Integer year;
	private Integer month;
	private Integer date;
	
	/**
	 * creates from java type
	 * @param companyId
	 * @param specialHolidayCode
	 * @param yearServiceCode
	 * @param yearServiceNo
	 * @param yearServiceType
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 * author: Hoang Yen
	 */
	public static YearServicePerSet createFromJavaType(String companyId, String specialHolidayCode, String yearServiceCode, int yearServiceNo, Integer year, Integer month, Integer date){
		return new YearServicePerSet(companyId, specialHolidayCode, yearServiceCode, yearServiceNo, year, month, date);
	}
	/**
	 * validate input list
	 * @param yearServicePerSetlst
	 * author: Hoang Yen
	 */
	public static void validateInput(List<YearServicePerSet> yearServicePerSetlst){
		List<Integer> yearLst = new ArrayList<>();
		for(YearServicePerSet item : yearServicePerSetlst){
			yearLst.add(item.getYear()); 
			// don't duplicate year
			yearLst.contains(item);
			// year must exist and year < 1 
			if(item.getYear() == null || item.getYear() < 1){
				throw new BusinessException("Msg_145");
			}
			// year and month must exsist
			if(item.getYear() == null || item.getMonth() == null){
				throw new BusinessException("Msg_100");
			}
			// date must exsist
			if(item.getDate() == null){
				throw new BusinessException("Msg_101");
			}
		}
	}
}
