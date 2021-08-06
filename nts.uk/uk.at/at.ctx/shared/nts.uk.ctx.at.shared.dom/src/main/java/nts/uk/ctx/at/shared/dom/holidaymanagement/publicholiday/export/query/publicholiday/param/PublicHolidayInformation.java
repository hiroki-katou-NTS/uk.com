package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param;


import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;

/**
 * 
 * @author hayata_maekawa
 *
 *		公休情報
 */
@Getter
@Setter
public class PublicHolidayInformation {

	/*
	 * 年月
	 */
	private YearMonth yearMonth; 
	
	/*
	 * 公休消化情報
	 */
	private PublicHolidayDigestionInformation publicHolidayDigestionInformation;
	
	/*
	 * 公休繰越情報
	 */
	private PublicHolidayCarryForwardInformation publicHolidayCarryForwardInformation;
		
	/*
	 * エラーリスト
	 */
	private Optional<PublicHolidayErrors> publicHolidayErrors;
	
	
	
	public PublicHolidayInformation(YearMonth yearMonth, 
			PublicHolidayDigestionInformation publicHolidayDigestionInformation,
			PublicHolidayCarryForwardInformation publicHolidayCarryForwardInformation,
			Optional<PublicHolidayErrors> publicHolidayErrors){
		
		this.yearMonth = yearMonth;
		this.publicHolidayDigestionInformation = publicHolidayDigestionInformation;
		this.publicHolidayCarryForwardInformation = publicHolidayCarryForwardInformation;
		this.publicHolidayErrors = publicHolidayErrors;
		
	}
}

