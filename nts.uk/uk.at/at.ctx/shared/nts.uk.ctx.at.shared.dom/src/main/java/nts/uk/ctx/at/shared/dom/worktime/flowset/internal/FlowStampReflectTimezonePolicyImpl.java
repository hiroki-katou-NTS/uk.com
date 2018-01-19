/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowStampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowStampReflectTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class FlowStampReflectTimezonePolicyImpl.
 */
@Stateless
public class FlowStampReflectTimezonePolicyImpl implements FlowStampReflectTimezonePolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowStampReflectTimezonePolicy#
	 * validate(nts.arc.error.BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting)
	 */
	@Override
	public void validate(PredetemineTimeSetting predetemineTimeSetting,
			FlowStampReflectTimezone flowStampReflectTimezone) {
		this.validateStampReflectTimezone(predetemineTimeSetting, flowStampReflectTimezone);
	}

	/**
	 * Validate stamp reflect timezone.
	 *
	 * @param predetemineTimeSetting
	 *            the predetemine time setting
	 * @param flowStampReflectTimezone
	 *            the flow stamp reflect timezone
	 */
	private void validateStampReflectTimezone(PredetemineTimeSetting predetemineTimeSetting,
			FlowStampReflectTimezone flowStampReflectTimezone) {
		// Msg_516
		TimeWithDayAttr startTime = predetemineTimeSetting.getStartDateClock();
		TimeWithDayAttr endTime = startTime.forwardByMinutes(predetemineTimeSetting.getRangeTimeDay().valueAsMinutes());
		flowStampReflectTimezone.getStampReflectTimezones().forEach(stampReflectTz -> {
			if (stampReflectTz.getStartTime().lessThan(startTime) || stampReflectTz.getEndTime().greaterThan(endTime)) {
				throw new BusinessException("Msg_516");
			}
		});
	}
}
