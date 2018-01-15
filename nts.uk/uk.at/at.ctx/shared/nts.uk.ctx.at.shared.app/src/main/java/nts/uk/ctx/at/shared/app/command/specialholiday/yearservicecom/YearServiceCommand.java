package nts.uk.ctx.at.shared.app.command.specialholiday.yearservicecom;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceset.YearServiceSet;

@Data
@AllArgsConstructor
public class YearServiceCommand {
	private int yearServiceNo;
	private int yearServiceType;
	private Integer year;
	private Integer month;
	private Integer date;
	
	/**
	 * Convert to domain
	 * @param specialHoliday special Holiday code 
	 * @param companyId company id
	 * @return YearServiceSet
	 */
	public YearServiceSet toDomain(String specialHoliday, String companyId) {
		return YearServiceSet.createFromJavaType(
				companyId, 
				specialHoliday, 
				yearServiceNo, 
				year, 
				month, 
				date);
	}
}
