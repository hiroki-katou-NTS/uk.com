/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class DiffTimeWorkStampReflectTimezone.
 */
// 時差勤務打刻反映時間帯
@Getter
public class DiffTimeWorkStampReflectTimezone extends WorkTimeDomainObject {

	/** The stamp reflect timezone. */
	// 打刻反映時間帯
	private List<StampReflectTimezone> stampReflectTimezone;

	/** The is update start time. */
	// 開始時刻に合わせて時刻を変動させる
	private boolean isUpdateStartTime;

	/**
	 * Instantiates a new diff time work stamp reflect timezone.
	 *
	 * @param memento
	 *            the memento
	 */
	public DiffTimeWorkStampReflectTimezone(DiffTimeStampReflectGetMemento memento) {
		this.stampReflectTimezone = memento.getStampReflectTimezone();
		this.isUpdateStartTime = memento.isIsUpdateStartTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(DiffTimeStampReflectSetMemento memento) {
		memento.setStampReflectTimezone(this.stampReflectTimezone);
		memento.setIsUpdateStartTime(this.isUpdateStartTime);
	}

	/**
	 * Correct data.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param oldDomain
	 *            the old domain
	 */
	public void correctData(ScreenMode screenMode, DiffTimeWorkStampReflectTimezone oldDomain) {
		Map<Entry<WorkNo, GoLeavingWorkAtr>, StampReflectTimezone> mapStampReflectTimezone = oldDomain.getStampReflectTimezone().stream()
				.collect(Collectors.toMap(
						item -> new ImmutablePair<WorkNo, GoLeavingWorkAtr>(item.getWorkNo(), item.getClassification()),
						Function.identity()));
		this.stampReflectTimezone.forEach(item -> item.correctData(screenMode, mapStampReflectTimezone
				.get(new ImmutablePair<WorkNo, GoLeavingWorkAtr>(item.getWorkNo(), item.getClassification()))));
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		this.stampReflectTimezone.forEach(item -> item.correctDefaultData(screenMode));
	}
}
