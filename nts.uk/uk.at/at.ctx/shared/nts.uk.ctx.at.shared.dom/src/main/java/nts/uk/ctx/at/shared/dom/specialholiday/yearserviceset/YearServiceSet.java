package nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;

@Getter
public class YearServiceSet {
	private String companyId;
	private int specialHolidayCode;
	private int yearServiceType;
	private Integer year;
	private Integer month;
	private Integer date;
	public YearServiceSet(String companyId, int specialHolidayCode, int yearServiceType, int year, int month, int date) {
		super();
		this.companyId = companyId;
		this.specialHolidayCode = specialHolidayCode;
		this.yearServiceType = yearServiceType;
		this.year = year;
		this.month = month;
		this.date = date;
	}
	public static YearServiceSet update(String companyId, int specialHolidayCode, int yearServiceType, int year, int month, int date){
		return new YearServiceSet(companyId, specialHolidayCode, yearServiceType, year, month, date);
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
	public static YearServiceSet createFromJavaType(String companyId, int specialHolidayCode, int yearServiceType, int year, int month, int date){
		return new YearServiceSet(companyId, specialHolidayCode, yearServiceType, year, month, date);
	}
	
	public static void validateInput(List<YearServiceSet> yearServiceSetlst){
		List<Integer> yearLst = new ArrayList<>();
		for(YearServiceSet item : yearServiceSetlst){
			yearLst.add(item.getYear()); 
			for(int i = 0; i < yearLst.size(); i++){
				int currentCondition = yearLst.get(i);
				if(currentCondition == item.getYear()){
					throw new BusinessException("Msg_99");
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
