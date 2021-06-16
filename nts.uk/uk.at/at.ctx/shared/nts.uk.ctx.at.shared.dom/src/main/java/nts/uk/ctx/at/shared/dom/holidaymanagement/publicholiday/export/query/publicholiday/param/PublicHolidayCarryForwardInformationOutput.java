package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param;

import java.util.List;

import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.carryForwarddata.PublicHolidayCarryForwardData;

/**
 * 公休繰越情報OUTPUT
 * @author hayata_maekawa
 *
 */
public class PublicHolidayCarryForwardInformationOutput {

	//公休繰越情報
	public PublicHolidayCarryForwardInformation publicHolidayCarryForwardInformation;
	
	//公休繰越データ
	public List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData;
	
	//コンストラクタ
	public PublicHolidayCarryForwardInformationOutput(
			PublicHolidayCarryForwardInformation carryForwardInformation, 
			List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData) {
		this.publicHolidayCarryForwardInformation = carryForwardInformation;
		this.publicHolidayCarryForwardData = publicHolidayCarryForwardData;
	
	}
}
