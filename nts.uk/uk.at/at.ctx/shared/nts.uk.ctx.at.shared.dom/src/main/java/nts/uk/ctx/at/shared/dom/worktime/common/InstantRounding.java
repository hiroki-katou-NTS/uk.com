/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class RoundingTime.
 */
// 時刻丸め
@Getter
@NoArgsConstructor
public class InstantRounding extends WorkTimeDomainObject implements Cloneable{

	/** The font rear section. */
	// 前後区分
	private FontRearSection fontRearSection;

	/** The rounding time unit. */
	// 時刻丸め単位
	private RoundingTimeUnit roundingTimeUnit;

	/**
	 * Instantiates a new instant rounding.
	 *
	 * @param fontRearSection
	 *            the font rear section
	 * @param roundingTimeUnit
	 *            the rounding time unit
	 */
	public InstantRounding(FontRearSection fontRearSection, RoundingTimeUnit roundingTimeUnit) {
		super();
		this.fontRearSection = fontRearSection;
		this.roundingTimeUnit = roundingTimeUnit;
	}

	/**
	 * Instantiates a new instant rounding.
	 *
	 * @param memento
	 *            the memento
	 */
	public InstantRounding(InstantRoundingGetMemento memento) {
		this.fontRearSection = memento.getFontRearSection();
		this.roundingTimeUnit = memento.getRoundingTimeUnit();
	}

	/**
	 * Save to mememto.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMememto(InstantRoundingSetMemento memento) {
		memento.setFontRearSection(this.fontRearSection);
		memento.setRoundingTimeUnit(this.roundingTimeUnit);
	}
	
	@Override
	public InstantRounding clone() {
		InstantRounding cloned = new InstantRounding();
		try {
			cloned.fontRearSection =FontRearSection.valueOf(this.fontRearSection.value);
			cloned.roundingTimeUnit = RoundingTimeUnit.valueOf(this.roundingTimeUnit.value);
		}
		catch (Exception e){
			throw new RuntimeException("InstantRounding clone error.");
		}
		return cloned;
	}
	
	/*
	 * 時刻丸め処理
	 */
	public WorkStamp roundStamp(WorkStamp workStamp) {

			//勤怠打刻．時刻を丸める 
		if (workStamp.getTimeDay().getTimeWithDay().isPresent()) {
			int blockTime = new Integer(this.getRoundingTimeUnit().description).intValue();
			int numberMinuteTimeOfDay = workStamp.getTimeDay().getTimeWithDay().get().v().intValue();
			int modTimeOfDay = numberMinuteTimeOfDay % blockTime;

			int timeChange = 0;
			boolean isBefore = this.getFontRearSection() == FontRearSection.BEFORE;
			if(isBefore) {
				timeChange = (modTimeOfDay ==0)? numberMinuteTimeOfDay:numberMinuteTimeOfDay - modTimeOfDay;
			}else {
				timeChange = (modTimeOfDay ==0)? numberMinuteTimeOfDay:numberMinuteTimeOfDay - modTimeOfDay + blockTime;
			}
			return new WorkStamp(
					new WorkTimeInformation(workStamp.getTimeDay().getReasonTimeChange(),new TimeWithDayAttr(timeChange))
					,workStamp.getLocationCode());
		}
		return workStamp;
	}

}
