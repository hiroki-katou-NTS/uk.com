package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

public interface WorkExpectation {
	
	/*
	 * 指定方法を取得する	
	 */
	public AssignmentMethod getAssignmentMethod();
	
	/*
	 * 休日の勤務希望である
	 */
	public boolean isHolidayExpectation();
	
	/*
	 * 希望に沿っているか
	 */
	public boolean isMatchingExpectation(Require require, 
			WorkInformation workInformation,
			List<TimeSpanForCalc> timeZoneList);
	
	/*
	 * 表示情報を返す
	 */
	public WorkExpectDisplayInfo getDisplayInformation(Require require);
	
	
	public static interface Require extends WorkInformation.Require{
		
		// 勤務情報からシフトマスタを取得する
		Optional<ShiftMaster> getShiftMasterByWorkInformation(WorkTypeCode workTypeCode, WorkTimeCode workTimeCode);

		// シフトマスタを取得する
		List<ShiftMaster> getShiftMaster(List<ShiftMasterCode> shiftMasterCodeList);
	}

}
