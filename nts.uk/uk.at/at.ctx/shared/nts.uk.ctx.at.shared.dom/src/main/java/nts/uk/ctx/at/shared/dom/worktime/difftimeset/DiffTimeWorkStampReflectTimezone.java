/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

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
	 * @param memento the memento
	 */
	public DiffTimeWorkStampReflectTimezone(DiffTimeStampReflectGetMemento memento)
	{
		this.stampReflectTimezone = memento.getStampReflectTimezone();
		this.isUpdateStartTime = memento.isIsUpdateStartTime();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DiffTimeStampReflectSetMemento memento)
	{
		memento.setStampReflectTimezone(this.stampReflectTimezone);
		memento.setIsUpdateStartTime(this.isUpdateStartTime);
	}
}
