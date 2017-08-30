package nts.uk.ctx.at.shared.dom.specialholiday.yearservicecom;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 
 * @author yennth
 *
 */
@Getter
public class YearServiceCom extends AggregateRoot{
	private String companyId;
	private int specialHolidayCode;
	private int lengthServiceYearAtr;
	public YearServiceCom(String companyId, int specialHolidayCode, int lengthServiceYearAtr) {
		super();
		this.companyId = companyId;
		this.specialHolidayCode = specialHolidayCode;
		this.lengthServiceYearAtr = lengthServiceYearAtr;
	}
	public static YearServiceCom createFromJavaType(String companyId, int specialHolidayCode, int lengthServiceYearAtr){
		return new YearServiceCom(companyId, specialHolidayCode, lengthServiceYearAtr);
	}
}
