package nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceset;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;

/**
 * 
 * @author yennth
 *
 */
@Setter
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
	public static List<String> validateInput(List<YearServiceSet> yearServiceSetlst){
		List<Integer> yearLst = new ArrayList<>();
		List<String> bugLst = new ArrayList<>();
		if(yearServiceSetlst.size() <= 0){
			new BusinessException("Msg_145");
			bugLst.add("Msg_145");
		}
		for(YearServiceSet item : yearServiceSetlst){ 
			// check duplicate year
			if(item.getYear() !=null && yearLst.contains(item.getYear())){
				new BusinessException("Msg_99");
				bugLst.add("Msg_99");
			} else {
				yearLst.add(item.getYear());
			}
			// year and month must exsist
			if(item.getYear() == null && item.getMonth() == null){
				new BusinessException("Msg_100");
				bugLst.add("Msg_100");
			}
			
			// date must exsist
			if(item.getDate() == null){
				new BusinessException("Msg_101");
				bugLst.add("Msg_101");
			}
			if(item.getYear() == null && item.getMonth()!=null && item.getDate()!=null){
				item.setYear(0);
			}
			if(item.getMonth() == null && item.getYear()!=null && item.getDate()!=null){
				item.setMonth(0);
			}
		}
		return bugLst;
	}
	
}
