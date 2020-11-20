package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;

/**
 * 一日分の勤務希望
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.勤務希望
 * @author dan_pv
 */
@AllArgsConstructor
@Getter
public class WorkAvailabilityOfOneDay implements DomainAggregate{
	
	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 希望日
	 */
	private GeneralDate workAvailabilityDate;
	
	/**
	 * メモ
	 */
	private WorkAvailabilityMemo memo;
	
	/**
	 * 勤務希望
	 */
	private WorkAvailability workAvailability;
	
	/**
	 * 
	 * @param employeeId 希望を出す従業員の社員ID
	 * @param availabilityDate 勤務希望の対象日
	 * @param memo 勤務希望のメモ
	 * @param assignmentMethod 勤務希望の指定方法
	 * @param shiftMasterCodeList シフトの勤務希望を出す場合のシフトマスタコードリスト
	 * @param timeZoneList 時間帯の勤務希望を出す場合の時間帯リスト
	 * @return
	 */
	public static WorkAvailabilityOfOneDay create(
			String employeeId,
			GeneralDate availabilityDate,
			WorkAvailabilityMemo memo,
			AssignmentMethod assignmentMethod,
			List<ShiftMasterCode> shiftMasterCodeList,
			List<TimeSpanForCalc> timeZoneList
			) {
		
		WorkAvailability workAvailability;
		switch (assignmentMethod) {
		
			case SHIFT:
				workAvailability = WorkAvailabilityByShiftMaster.create(shiftMasterCodeList); break;
				
			case TIME_ZONE:
				workAvailability = WorkAvailabilityByTimeZone.create(timeZoneList); break;
				
			case HOLIDAY: default:
				workAvailability = new WorkAvailabilityByHoliday(); break;
		}
		
		return new WorkAvailabilityOfOneDay(employeeId, availabilityDate, memo, workAvailability);
	}
	
	/**
	 * 休日の勤務希望である	
	 * 
	 * @return 出した勤務希望が休日の種類かどうか
	 */
	public boolean isHolidayAvailability() {
		return this.workAvailability.isHolidayAvailability();
	}
	
	/**
	 * 希望に沿っているか
	 * @param require 
	 * @param workInformation チェックしたい勤務情報
	 * @param timeZoneList チェックしたい時間帯リスト
	 * @return
	 */
	public boolean isMatchingAvailability(Require require, 
			WorkInformation workInformation, 
			List<TimeSpanForCalc> timeZoneList) {
		
		return this.workAvailability.isMatchingWorkAvailability(require, workInformation, timeZoneList);
				
	}
	
	/**
	 * 表示情報を返す
	 * @param require
	 * @return
	 */
	public WorkAvailabilityDisplayInfoOfOneDay getDisplayInformation(Require require) {
		WorkAvailabilityDisplayInfo displayInfo = this.workAvailability.getDisplayInformation(require);
		return new WorkAvailabilityDisplayInfoOfOneDay(employeeId, workAvailabilityDate, memo, displayInfo);
	}
	
	public static interface Require extends WorkAvailability.Require {
		
	}

}
