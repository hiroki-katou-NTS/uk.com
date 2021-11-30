/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.TreatLateEarlyTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySetGetMemento;

/**
 * The Class WorkTimezoneLateEarlySetDto.
 */
@Value
public class WorkTimezoneLateEarlySetDto implements WorkTimezoneLateEarlySetGetMemento {

	/** The common set. */
	private EmTimezoneLateEarlyCommonSetDto commonSet;

	/** The other class set. */
	private List<OtherEmTimezoneLateEarlySetDto> otherClassSets;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneLateEarlySetGetMemento#getCommonSet()
	 */
	@Override
	public TreatLateEarlyTime getCommonSet() {
		return new TreatLateEarlyTime(this.commonSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneLateEarlySetGetMemento#getOtherClassSet()
	 */
	@Override
	public List<OtherEmTimezoneLateEarlySet> getOtherClassSet() {
		return this.otherClassSets.stream().map(item -> new OtherEmTimezoneLateEarlySet(item))
				.collect(Collectors.toList());
	}
}
