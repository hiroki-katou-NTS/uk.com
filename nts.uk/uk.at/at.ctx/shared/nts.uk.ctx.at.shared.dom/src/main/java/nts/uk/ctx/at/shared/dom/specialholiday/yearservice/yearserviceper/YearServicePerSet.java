package nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
@Setter
@Getter
@AllArgsConstructor
public class YearServicePerSet {
	/**会社ID**/
	private String companyId;
	
	private int specialHolidayCode;
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
	public static YearServicePerSet createFromJavaType(String companyId, int specialHolidayCode, String yearServiceCode, int yearServiceNo, Integer year, Integer month, Integer date){
		return new YearServicePerSet(companyId, specialHolidayCode, yearServiceCode, yearServiceNo, year, month, date);
	}
	/**
	 * validate input list
	 * @param yearServicePerSetlst
	 * author: Hoang Yen
	 */
	public static List<String> validateInput(List<YearServicePerSet> yearServicePerSetlst){
		List<Integer> yearLst = new ArrayList<>();
		List<String> bugLst = new ArrayList<>();
		// have at least a item
		if(yearServicePerSetlst.size() == 0){
			new BusinessException("Msg_145");
			bugLst.add("Msg_145");
		}
		for(YearServicePerSet item : yearServicePerSetlst){
			if((item.getYear() != null && yearLst.contains(item.getYear())) || (item.getYear() == null && yearLst.contains(item.getYear()))){
				new BusinessException("Msg_99");
				bugLst.add("Msg_99");
			}else{
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
