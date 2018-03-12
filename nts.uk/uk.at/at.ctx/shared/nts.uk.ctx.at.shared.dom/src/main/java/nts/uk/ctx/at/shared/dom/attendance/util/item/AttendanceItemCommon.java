package nts.uk.ctx.at.shared.dom.attendance.util.item;

public abstract class AttendanceItemCommon implements ConvertibleAttendanceItem{

	private boolean haveData = false;
	
	public boolean isHaveData() {
		return this.haveData;
	}
	
	public void exsistData() {
		this.haveData = true;
	}
}
