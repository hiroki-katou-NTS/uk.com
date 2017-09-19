/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.outsideot.data;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.OutsideOTSettingDto;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.PremiumExtra60HRateDto;

/**
 * The Class OutsideOTSettingData.
 */

@Getter
@Setter
public class OutsideOTSettingData {

	/** The setting. */
	private OutsideOTSettingDto setting;
	
	/** The language data. */
	private List<OvertimeNameLanguageData> overtimeLanguageData;
	
	/** The breakdown language data. */
	private List<OutsideOTBRDItemNameLangData> breakdownLanguageData;
	
	
	/** The premium extra rates. */
	private List<PremiumExtra60HRateDto> premiumExtraRates;
	
	/** The map attendance item. */
	private Map<Integer, DailyAttendanceItem> mapAttendanceItem;
	
}
