package nts.uk.ctx.bs.employee.app.find.holidaysetting.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class PublicHolidayMonthSettingDto.
 */
@Data
@NoArgsConstructor
public class PublicHolidayMonthSettingDto {
	
	/** The public hd management year. */
	public int publicHdManagementYear;
	
	/** The month. */
	public int month;

	/** The in legal holiday. */
	public double inLegalHoliday;
	
	/**
	 * Instantiates a new public holiday month setting dto.
	 */
	public PublicHolidayMonthSettingDto(int publicHdManagementYear, int month, double inLegalHoliday){
		this.publicHdManagementYear = publicHdManagementYear;
		this.month = month;
		this.inLegalHoliday = inLegalHoliday;
	}
}
