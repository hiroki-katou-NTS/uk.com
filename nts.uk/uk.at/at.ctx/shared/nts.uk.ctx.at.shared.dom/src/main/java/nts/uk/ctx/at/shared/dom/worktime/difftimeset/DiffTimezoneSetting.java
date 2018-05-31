/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class DiffTimezoneSetting.
 */
// 時差勤務時間帯設定
@Getter
public class DiffTimezoneSetting extends WorkTimeDomainObject {

	/** The employment timezones. */
	// 就業時間帯
	private List<EmTimeZoneSet> employmentTimezones;

	/** The o T timezones. */
	// 残業時間帯
	private List<DiffTimeOTTimezoneSet> oTTimezones;

	/**
	 * Instantiates a new diff timezone setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public DiffTimezoneSetting(DiffTimezoneSettingGetMemento memento) {
		this.employmentTimezones = memento.getEmploymentTimezones();
		this.oTTimezones = memento.getOTTimezones();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(DiffTimezoneSettingSetMemento memento) {
		memento.setEmploymentTimezones(this.employmentTimezones);
		memento.setOTTimezones(this.oTTimezones);
	}

	/**
	 * Correct data.
	 *
	 * @param other
	 *            the other
	 */
	public void correctData(DiffTimezoneSetting other) {
		// restore 就業時間帯
		Map<EmTimeFrameNo, EmTimeZoneSet> mapEmTimezone = other.getEmploymentTimezones().stream().collect(
				Collectors.toMap(item -> ((EmTimeZoneSet) item).getEmploymentTimeFrameNo(), Function.identity()));
		this.employmentTimezones.forEach(emTimezoneOther -> {
			emTimezoneOther.restoreData(mapEmTimezone.get(emTimezoneOther.getEmploymentTimeFrameNo()));
		});

		// restore 残業時間帯
		this.oTTimezones = other.getOTTimezones();
	}

	/**
	 * Correct default data.
	 */
	public void correctDefaultData() {
		this.oTTimezones.forEach(item -> item.correctDefaultData());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject#validate()
	 */
	@Override
	public void validate() {

		// Validate overlap employmentTimezones
		for (int i = 0; i < this.employmentTimezones.size(); i++) {
			EmTimeZoneSet em = this.employmentTimezones.get(i);
			for (int j = i + 1; j < this.employmentTimezones.size(); j++) {
				EmTimeZoneSet em2 = this.employmentTimezones.get(j);
				// check overlap
				if (em.getTimezone().isOverlap(em2.getTimezone())) {
					this.bundledBusinessExceptions.addMessage("Msg_515", "KMK003_86");
				}
			}
		}

		// validate overlap oTTimezones
		for (int i = 0; i < this.oTTimezones.size(); i++) {
			DiffTimeOTTimezoneSet em = this.oTTimezones.get(i);
			for (int j = i + 1; j < this.oTTimezones.size(); j++) {
				DiffTimeOTTimezoneSet em2 = this.oTTimezones.get(j);
				// check overlap
				if (em.getTimezone().isOverlap(em2.getTimezone())) {
					this.bundledBusinessExceptions.addMessage("Msg_515", "KMK003_89");
				}
			}
		}

		super.validate();
	}

	/**
	 * Check over time and em time overlap.
	 */
	public boolean isOverTimeAndEmTimeOverlap() {
		if (CollectionUtil.isEmpty(this.employmentTimezones) || CollectionUtil.isEmpty(this.oTTimezones)) {
			return false;
		}		
		return this.oTTimezones.stream().anyMatch(ot -> this.employmentTimezones.stream().anyMatch(em -> ot.getTimezone().isOverlap(em.getTimezone())));
	}

	/**
	 * Sets the employment timezones.
	 *
	 * @param employmentTimezones
	 *            the new employment timezones
	 */
	public void setEmploymentTimezones(List<EmTimeZoneSet> employmentTimezones) {
		this.employmentTimezones = employmentTimezones;
	}

	/**
	 * Sets the OT timezones.
	 *
	 * @param oTTimezones
	 *            the new OT timezones
	 */
	public void setOTTimezones(List<DiffTimeOTTimezoneSet> oTTimezones) {
		this.oTTimezones = oTTimezones;
	}
}
