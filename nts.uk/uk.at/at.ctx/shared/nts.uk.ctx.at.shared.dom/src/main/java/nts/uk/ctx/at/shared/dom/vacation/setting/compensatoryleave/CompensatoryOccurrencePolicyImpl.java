/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;

/**
 * The Class CompensatoryOccurrencePolicyImpl.
 */
@Stateless
public class CompensatoryOccurrencePolicyImpl implements CompensatoryOccurrencePolicy{

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
	 * CompensatoryOccurrencePolicy#validate(nts.arc.error.BundledBusinessException, 
	 * java.util.List, nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting)
	 */
	@Override
	public void validate(BundledBusinessException bundledBusinessExceptions, List<CompensatoryOccurrenceSetting> compensatoryOccurrenceSetting) {
		compensatoryOccurrenceSetting.forEach(c -> {
			DesignatedTime dt = c.getTransferSetting().getDesignatedTime();
			if (dt.getOneDayTime().lessThan(dt.getHalfDayTime())) {
				BusinessException be = new BusinessException("Msg_782");
				be.setSuppliment("occurrenceType", c.getOccurrenceType().value);
				bundledBusinessExceptions.addMessage(be);
			}
		});
	}
}
