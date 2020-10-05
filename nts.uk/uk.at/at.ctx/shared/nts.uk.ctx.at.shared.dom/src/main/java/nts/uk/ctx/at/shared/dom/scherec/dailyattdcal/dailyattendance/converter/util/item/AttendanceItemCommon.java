package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item;

import java.io.Serializable;

public abstract class AttendanceItemCommon implements ConvertibleAttendanceItem, Serializable {

	/** */
	private static final long serialVersionUID = 1L;
	private boolean haveData = false;

	@Override
	public boolean isHaveData() {
		return this.haveData;
	}

	@Override
	public void exsistData() {
		this.haveData = true;
	}
}
