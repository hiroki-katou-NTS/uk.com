package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import java.util.List;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 勤務希望
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.勤務希望
 * @author dan_pv
 *
 */
public interface WorkAvailability {
	
	/**
	 * 指定方法を取得する	
	 * @return
	 */
	public AssignmentMethod getAssignmentMethod();
	
	/**
	 * 休日の勤務希望である
	 */
	public default boolean isHolidayAvailability(){ 
		return this.getAssignmentMethod() == AssignmentMethod.HOLIDAY;
    } 
	
	/**
	 * 希望に沿っているか
	 * @param require
	 * @param workInformation
	 * @param timeZoneList
	 * @return
	 */
	public boolean isMatchingWorkAvailability(Require require, 
			WorkInformation workInformation,
			List<TimeSpanForCalc> timeZoneList);
	
	/**
	 * 表示情報を返す
	 * @param require
	 * @return
	 */
	public WorkAvailabilityDisplayInfo getDisplayInformation(Require require);
	
	
	public static interface Require extends WorkAvailabilityByHoliday.Require, WorkAvailabilityByShiftMaster.Require {
		
	}

}
