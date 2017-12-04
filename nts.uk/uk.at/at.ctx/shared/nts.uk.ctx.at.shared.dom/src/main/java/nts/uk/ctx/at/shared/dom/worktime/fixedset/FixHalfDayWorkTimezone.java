/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime_old.AmPmClassification;

/**
 * The Class FixHalfDayWorkTimezone.
 */
// 固定勤務の平日出勤用勤務時間帯
@Getter
@Builder
public class FixHalfDayWorkTimezone extends DomainObject {
	
	/** The rest time zone. */
	//休憩時間帯 
	private FixRestTimezoneSet restTimeZone;
	
	/** The work timezone. */
	//勤務時間帯
	private FixedWorkTimezoneSet workTimezone;
	
	/** The day atr. */
	//午前午後区分
	private AmPmClassification dayAtr;

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		
		List<String> lstWorkTime = this.workTimezone.getLstWorkingTimezone().stream()
				.map(item -> item.getTimezone().toString())
				.collect(Collectors.toList());
		List<String> lstOTTTime = this.workTimezone.getLstOTTimezone().stream()
				.map(item -> item.getTimezone().toString())
				.collect(Collectors.toList());
		
		this.restTimeZone.getLstTimezone().forEach((timezone) -> {
			// has in 就業時間帯.時間帯
			boolean isHasWorkTime = lstWorkTime.contains(timezone.toString());
			
			// has in 残業時間帯.時間帯
			boolean isHasOTTTime = lstOTTTime.contains(timezone.toString());
			
			if (!isHasWorkTime && !isHasOTTTime) {
				throw new BusinessException("Msg_755");
			}
		});
	}
	
}
