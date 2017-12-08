/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset.internal;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FixedWorkSettingPolicyImpl.
 */
@Stateless
public class FixedWorkSettingPolicyImpl implements FixedWorkSettingPolicy {

//	@Inject
//	private PredeteminePolicyService predService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingPolicy#
	 * canRegister(nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSet)
	 */
	@Override

	public void canRegister(FixedWorkSetting fixedWorkSetting, PredetemineTimeSetting predetemineTimeSet) {
		
		//TODO
//		// Check #Msg_516 domain StampReflectTimezone
//		fixedWorkSetting.getLstStampReflectTimezone().forEach(setting -> {
//			this.predService.validateOneDay(predetemineTimeSet, setting.getStartTime(), setting.getEndTime());
//		});
//		
//		// Check #Msg_516 domain HDWorkTimeSheetSetting
//		fixedWorkSetting.getOffdayWorkTimezone().getLstWorkTimezone().forEach(setting -> {
//			this.predService.validateOneDay(predetemineTimeSet, setting.getTimezone().getStart(), setting.getTimezone().getEnd());
//		});

	}

}
