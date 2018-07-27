/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowHalfDayWtzPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowOffdayWtzPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowStampReflectTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowTimeSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;

/**
 * The Class FlowWorkSettingPolicyImpl.
 */
@Stateless
public class FlowWorkSettingPolicyImpl implements FlowWorkSettingPolicy {

	/** The flow stamp reflect timezone policy. */
	@Inject
	private FlowStampReflectTimezonePolicy flowStampReflectTimezonePolicy;

	/** The flow time setting policy. */
	@Inject
	private FlowTimeSettingPolicy flowTimeSettingPolicy;

	/** The flow half policy. */
	@Inject
	private FlowHalfDayWtzPolicy flowHalfPolicy;

	/** The flow off policy. */
	@Inject
	private FlowOffdayWtzPolicy flowOffPolicy;

	/** The wtz common set policy. */
	@Inject
	private WorkTimezoneCommonSetPolicy wtzCommonSetPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingPolicy#validate(
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting)
	 */
	@Override
	public void validate(BundledBusinessException bundledBusinessExceptions,
			PredetemineTimeSetting predetemineTimeSetting, WorkTimeDisplayMode displayMode,
			FlowWorkSetting flowWorkSetting) {

		// Msg_516
		if (DisplayMode.DETAIL.equals(displayMode.getDisplayMode())) {
			this.flowStampReflectTimezonePolicy.validate(bundledBusinessExceptions, predetemineTimeSetting,
					flowWorkSetting.getStampReflectTimezone());
		}

		// Msg_781
		flowWorkSetting.getHalfDayWorkTimezone().getWorkTimeZone().getLstOTTimezone().forEach(flowOTTimezone -> {
			this.flowTimeSettingPolicy.validate(bundledBusinessExceptions, predetemineTimeSetting,
					flowOTTimezone.getFlowTimeSetting());
		});

		// validate FlowHalfDay
		this.flowHalfPolicy.validate(bundledBusinessExceptions, predetemineTimeSetting,
				flowWorkSetting.getHalfDayWorkTimezone());

		// validate FlowOffDay
		this.flowOffPolicy.validate(bundledBusinessExceptions, predetemineTimeSetting,
				flowWorkSetting.getOffdayWorkTimezone());

		// Validate WorkTimezoneCommonSet
		this.wtzCommonSetPolicy.validate(bundledBusinessExceptions, predetemineTimeSetting,
				flowWorkSetting.getCommonSetting());
	}

}
