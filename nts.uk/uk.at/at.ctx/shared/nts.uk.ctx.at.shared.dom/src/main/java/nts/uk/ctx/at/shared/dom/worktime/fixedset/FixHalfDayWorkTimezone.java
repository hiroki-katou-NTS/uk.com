/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime_old.AmPmClassification;

/**
 * The Class FixHalfDayWorkTimezone.
 */
@Getter
// 固定勤務の平日出勤用勤務時間帯
public class FixHalfDayWorkTimezone extends DomainObject {

	/** The rest timezone. */
	// 休憩時間帯
	private FixRestTimezoneSet restTimezone;

	/** The work timezone. */
	// 勤務時間帯
	private FixedWorkTimezoneSet workTimezone;

	/** The day atr. */
	// 午前午後区分
	private AmPmClassification dayAtr;

	/**
	 * Instantiates a new fix half day work timezone.
	 *
	 * @param memento
	 *            the memento
	 */
	public FixHalfDayWorkTimezone(FixHalfDayWorkTimezoneGetMemento memento) {
		this.restTimezone = memento.getRestTimezone();
		this.workTimezone = memento.getWorkTimezone();
		this.dayAtr = memento.getDayAtr();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(FixHalfDayWorkTimezoneSetMemento memento) {
		memento.setRestTimezone(this.restTimezone);
		memento.setWorkTimezone(this.workTimezone);
		memento.setDayAtr(this.dayAtr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();

		// Validate #Msg_755
		this.restTimezone.getLstTimezone().forEach((timezone) -> {
			// Is timezone in WorkingTimezone - 就業時間帯.時間帯
			boolean isHasWorkTime = this.workTimezone.getLstWorkingTimezone().stream()
					.map(item -> item.getTimezone())
					.anyMatch((timeZoneRounding) -> {
						return timezone.getStart().greaterThanOrEqualTo(timeZoneRounding.getStart())
								&& timezone.getEnd().lessThanOrEqualTo(timeZoneRounding.getEnd());
					});

			// Is timezone in OTTimezone - 残業時間帯.時間帯
			boolean isHasOTTTime = this.workTimezone.getLstOTTimezone().stream()
					.map(item -> item.getTimezone())
					.anyMatch((timeZoneRounding) -> {
						return timezone.getStart().greaterThanOrEqualTo(timeZoneRounding.getStart())
								&& timezone.getEnd().lessThanOrEqualTo(timeZoneRounding.getEnd());
					});

			// Throw exception if not match any condition
			if (!isHasWorkTime && !isHasOTTTime) {
				throw new BusinessException("Msg_755");
			}
		});
	}

}
