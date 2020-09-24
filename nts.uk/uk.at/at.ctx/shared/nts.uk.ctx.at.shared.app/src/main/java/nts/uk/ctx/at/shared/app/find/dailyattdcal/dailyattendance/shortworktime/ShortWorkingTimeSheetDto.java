package nts.uk.ctx.at.shared.app.find.dailyattdcal.dailyattendance.shortworktime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ChildCareAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ShortWorkingTimeSheetDto {
	/** 短時間勤務枠NO: 短時間勤務枠NO*/
	private int shortWorkTimeFrameNo;
	
	/** 育児介護区分: 育児介護区分*/
	private int childCareAttr;

	/** 開始: 時刻(日区分付き) */
	private int startTime;
	
	/** 終了: 時刻(日区分付き) */
	private int endTime;
	
	/** 控除時間: 勤怠時間 */
	private int deductionTime;
	
	/** 時間: 勤怠時間 */
	private int shortTime;
	
	public static ShortWorkingTimeSheetDto fromDomain(ShortWorkingTimeSheet shortWorkingTimeSheet) {
		return new ShortWorkingTimeSheetDto(
				shortWorkingTimeSheet.getShortWorkTimeFrameNo().v(), 
				shortWorkingTimeSheet.getChildCareAttr().value, 
				shortWorkingTimeSheet.getStartTime().v(), 
				shortWorkingTimeSheet.getEndTime().v(), 
				shortWorkingTimeSheet.getDeductionTime().v(), 
				shortWorkingTimeSheet.getShortTime().v());
	}
	
	public ShortWorkingTimeSheet toDomain() {
		return new ShortWorkingTimeSheet(
				new ShortWorkTimFrameNo(shortWorkTimeFrameNo), 
				EnumAdaptor.valueOf(childCareAttr, ChildCareAttribute.class), 
				new TimeWithDayAttr(startTime), 
				new TimeWithDayAttr(endTime), 
				new AttendanceTime(deductionTime) , 
				new AttendanceTime(shortTime));
	}
}
