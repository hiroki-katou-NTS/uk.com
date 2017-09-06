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
	private int yearServiceType;
	private Integer year;
	private Integer month;
	private Integer date;
//	public YearServiceSet(String companyId, String specialHolidayCode, int yearServiceNo, int yearServiceType, int year, Integer month, Integer date) {
//		super();
//		this.companyId = companyId;
//		this.specialHolidayCode = specialHolidayCode;
//		this.yearServiceNo = yearServiceNo;
//		this.yearServiceType = yearServiceType;
//		this.year = year;
//		this.month = month;
//		this.date = date;
//	}
	public static YearServiceSet update(String companyId, String specialHolidayCode, int yearServiceNo, int yearServiceType, Integer year, Integer month, Integer date){
		return new YearServiceSet(companyId, specialHolidayCode, yearServiceNo, yearServiceType, year, month, date);
	}
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
	public static YearServiceSet createFromJavaType(String companyId, String specialHolidayCode, int yearServiceNo, int yearServiceType, Integer year, Integer month, Integer date){
		return new YearServiceSet(companyId, specialHolidayCode, yearServiceNo, yearServiceType, year, month, date);
	}
	
	public static void validateInput(List<YearServiceSet> yearServiceSetlst){
		List<Integer> yearLst = new ArrayList<>();
		for(YearServiceSet item : yearServiceSetlst){
			yearLst.add(item.getYear()); 
			for(int i = 0; i < (yearLst.size()-2); i++){
				for(int k = i+1; k<yearLst.size(); k++ ){
					if( yearLst.get(i) == yearLst.get(k)){
						throw new BusinessException("Msg_99");
					}
				}
			}
			if(item.getYear() == null || item.getYear() < 1){
				throw new BusinessException("Msg_145");
			}
			if(item.getYear() == null || item.getMonth() == null){
				throw new BusinessException("Msg_100");
			}
			if(item.getDate() == null){
				throw new BusinessException("Msg_101");
			}
		}
		
	}
	
}
