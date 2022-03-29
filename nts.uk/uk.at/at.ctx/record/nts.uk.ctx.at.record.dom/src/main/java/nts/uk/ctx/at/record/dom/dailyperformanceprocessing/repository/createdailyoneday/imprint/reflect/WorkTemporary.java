package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflect;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;

/**
 * 勤務Temporary
 * @author phongtq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorkTemporary {
	// 出勤２時刻
	private Optional<TimeActualStamp> twoHoursWork;
	// 最初の出勤
	private Optional<TimeActualStamp> firstAttendance;
	// 最後の退勤
	private Optional<TimeActualStamp> lastLeave;
	// 退勤１時刻
	private Optional<TimeActualStamp> oneHourLeavingWork;
	public void setTwoHoursWork(Optional<TimeActualStamp> twoHoursWork) {
		this.twoHoursWork = twoHoursWork;
	}
	public void setFirstAttendance(Optional<TimeActualStamp> firstAttendance) {
		this.firstAttendance = firstAttendance;
	}
	public void setLastLeave(Optional<TimeActualStamp> lastLeave) {
		this.lastLeave = lastLeave;
	}
	public void setOneHourLeavingWork(Optional<TimeActualStamp> oneHourLeavingWork) {
		this.oneHourLeavingWork = oneHourLeavingWork;
	}
	
	public Optional<TimeActualStamp> getAutoFirstAttendance(){
		return this.getFirstAttendance().map(c->c.createByAutomaticSet());
	}
	public Optional<TimeActualStamp> getAutoTwoHoursWork(){
		return this.getTwoHoursWork().map(c->c.createByAutomaticSet());
	}
	public Optional<TimeActualStamp> getAutoLastLeave(){
		return this.getLastLeave().map(c->c.createByAutomaticSet());
	}
	public Optional<TimeActualStamp> getAutoOneHourLeavingWork(){
		return this.getOneHourLeavingWork().map(c->c.createByAutomaticSet());
	}
}
