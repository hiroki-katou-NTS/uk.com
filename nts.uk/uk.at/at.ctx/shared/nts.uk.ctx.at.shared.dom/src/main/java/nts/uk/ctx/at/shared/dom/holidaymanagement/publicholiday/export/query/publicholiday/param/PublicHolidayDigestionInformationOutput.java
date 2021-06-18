package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公休消化情報OUTPUT
 * @author hayata_maekawa
 *
 */
@AllArgsConstructor
@Getter
public class PublicHolidayDigestionInformationOutput {

	//公休消化情報
	private PublicHolidayDigestionInformation publicHolidayDigestionInformation;
	
	//公休エラー
	private Optional<PublicHolidayErrors>  errors;
	
}
