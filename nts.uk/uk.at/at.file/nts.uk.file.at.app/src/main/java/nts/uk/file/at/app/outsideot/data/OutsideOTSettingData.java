/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.outsideot.data;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.OutsideOTSettingDto;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.PremiumExtra60HRateDto;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.SuperHD60HConMedDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;

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
	
	/** The super HD 60 H con med. */
	private SuperHD60HConMedDto superHD60HConMed;
	
	/** The map attendance item. */
	private Map<Integer, DailyAttendanceItem> mapAttendanceItem;
	
}
