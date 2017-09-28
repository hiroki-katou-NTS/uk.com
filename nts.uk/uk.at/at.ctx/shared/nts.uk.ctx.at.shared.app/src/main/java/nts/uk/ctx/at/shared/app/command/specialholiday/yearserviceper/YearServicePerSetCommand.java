package nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceper;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper.YearServicePerSet;

@Data

public class YearServicePerSetCommand{
	/**コード**/
	private String specialHolidayCode;
	private String yearServiceCode;
	private int yearServiceNo;
	/** 年数 **/
	private Integer year;
	/** 月数 **/
	private Integer month;
	/** 特別休暇付与日数 **/
	private Integer date;
	public YearServicePerSet toDomainPerSet(String companyId, String specialHolidayCode, String yearServiceCode, int yearServiceNo){
		return YearServicePerSet.createFromJavaType(companyId, 
													specialHolidayCode, 
													yearServiceCode, 
													yearServiceNo, 
													year, 
													month, 
													date);
	}
}
