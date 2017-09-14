package nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;

@Getter
@AllArgsConstructor
public class YearServiceSet {
	private String companyId;
	private String specialHolidayCode; 
	private int yearServiceNo;
	private Integer year;
	private Integer month;
	private Integer date;

	/**
	 * creates from java type 
	 * @param companyId
	 * @param specialHolidayCode
	 * @param yearServiceType
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static YearServiceSet createFromJavaType(String companyId, String specialHolidayCode, int yearServiceNo, Integer year, Integer month, Integer date){
		return new YearServiceSet(companyId, specialHolidayCode, yearServiceNo, year, month, date);
	}
	
	/**
	 * validate list input
	 * @param yearServiceSetlst
	 */
	public static void validateInput(List<YearServiceSet> yearServiceSetlst){
		List<Integer> yearLst = new ArrayList<>();
		for(YearServiceSet item : yearServiceSetlst){
			yearLst.add(item.getYear()); 
			// check duplicate year
			yearLst.contains(item);
			// year must exsist and year < 1
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
