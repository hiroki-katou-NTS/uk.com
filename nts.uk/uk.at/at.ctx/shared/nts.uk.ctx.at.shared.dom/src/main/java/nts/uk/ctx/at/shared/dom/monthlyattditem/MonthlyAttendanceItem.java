/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.monthlyattditem;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceName;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;

/**
 * The Class MonthlyAttendanceItem.
 */
@Getter
// 月次の勤怠項目
public class MonthlyAttendanceItem extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The attendance item id. */
	// 勤怠項目ID
	private int attendanceItemId;

	/** The attendance name. */
	// 勤怠項目名称
	private AttendanceName attendanceName;

	/** The display number. */
	// 表示番号
	private int displayNumber;

	/** The user can update atr. */
	// 使用区分
	private UseSetting userCanUpdateAtr;

	/** The monthly attendance atr. */
	// 勤怠項目属性
	private MonthlyAttendanceItemAtr monthlyAttendanceAtr;

	/** The name line feed position. */
	// ユーザーが値を変更できる
	private int nameLineFeedPosition;

	/**
	 * Instantiates a new monthly attendance item.
	 *
	 * @param memento the memento
	 */
	public MonthlyAttendanceItem(MonthlyAttendanceItemGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.attendanceItemId = memento.getAttendanceItemId();
		this.attendanceName = memento.getAttendanceName();
		this.displayNumber = memento.getDisplayNumber();
		this.userCanUpdateAtr = memento.getUserCanUpdateAtr();
		this.monthlyAttendanceAtr = memento.getMonthlyAttendanceAtr();
		this.nameLineFeedPosition = memento.getNameLineFeedPosition();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(MonthlyAttendanceItemSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setAttendanceItemId(this.attendanceItemId);
		memento.setAttendanceName(this.attendanceName);
		memento.setDisplayNumber(this.displayNumber);
		memento.setUserCanUpdateAtr(this.userCanUpdateAtr);
		memento.setMonthlyAttendanceAtr(this.monthlyAttendanceAtr);
		memento.setNameLineFeedPosition(this.nameLineFeedPosition);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + attendanceItemId;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonthlyAttendanceItem other = (MonthlyAttendanceItem) obj;
		if (attendanceItemId != other.attendanceItemId)
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}

}
