package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 休出枠時間帯
 * @author ken_takasu
 *
 */
@Getter
@AllArgsConstructor
public class HolidayWorkFrameTimeSheet implements Cloneable{
	//休出枠No
	private HolidayWorkFrameNo holidayWorkTimeSheetNo;
	//時間帯
	private TimeSpanForCalc timeSheet;
	
	//【追加予定】計算休出時間
	@Setter
	private AttendanceTime hdTimeCalc;
	
	//【追加予定】計算振替休出時間
	@Setter
	private AttendanceTime tranferTimeCalc;
	/**
	 * Constructor
	 */
	public HolidayWorkFrameTimeSheet(HolidayWorkFrameNo holidayWorkTimeSheetNo, TimeSpanForCalc timeSheet) {
		super();
		this.holidayWorkTimeSheetNo = holidayWorkTimeSheetNo;
		this.timeSheet = timeSheet;
		this.hdTimeCalc = new AttendanceTime(0);
		this.tranferTimeCalc = new AttendanceTime(0);
	}
	
	@Override
	protected HolidayWorkFrameTimeSheet clone() {
		return new HolidayWorkFrameTimeSheet(new HolidayWorkFrameNo(holidayWorkTimeSheetNo.v()),
				new TimeSpanForCalc(new TimeWithDayAttr(timeSheet.getStart().v()),
						new TimeWithDayAttr(timeSheet.getEnd().v())),
				new AttendanceTime(hdTimeCalc.v()), new AttendanceTime(tranferTimeCalc.v()));
	}
}
