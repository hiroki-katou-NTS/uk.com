package nts.uk.ctx.bs.employee.app.find.holidaysetting.common.dto;

import lombok.Data;

/**
 * The Class PublicHolidayMonthSettingDto.
 */
@Data
public class PublicHolidayMonthSettingDto {
	
	/** The public hd management year. */
	private int publicHdManagementYear;
	
	/** The month. */
	private int month;

	/** The in legal holiday. */
	private int inLegalHoliday;
		
	/** The out legal holiday. */
	private int outLegalHoliday;
	
	/**
	 * Instantiates a new public holiday month setting dto.
	 */
	public PublicHolidayMonthSettingDto(int publicHdManagementYear, int month, int inLegalHoliday, int outLegalHoliday){
		this.publicHdManagementYear = publicHdManagementYear;
		this.month = month;
		this.inLegalHoliday = inLegalHoliday;
		this.outLegalHoliday = outLegalHoliday;
	}
}
