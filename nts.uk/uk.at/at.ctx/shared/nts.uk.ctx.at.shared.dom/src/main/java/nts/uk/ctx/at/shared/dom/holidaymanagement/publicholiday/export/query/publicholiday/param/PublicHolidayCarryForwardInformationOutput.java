package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.carryForwarddata.PublicHolidayCarryForwardData;

/**
 * 公休繰越情報OUTPUT
 * @author hayata_maekawa
 *
 */
@AllArgsConstructor
@Getter
public class PublicHolidayCarryForwardInformationOutput {

	//公休繰越情報
	private PublicHolidayCarryForwardInformation publicHolidayCarryForwardInformation;
	
	//公休繰越データ
	private List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData;
	

}
