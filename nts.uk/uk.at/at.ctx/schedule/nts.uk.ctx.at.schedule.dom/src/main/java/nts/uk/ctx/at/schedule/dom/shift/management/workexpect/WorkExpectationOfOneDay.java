package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

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
public class WorkExpectationOfOneDay implements DomainAggregate{
	
	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 希望日
	 */
	private GeneralDate expectingDate;
	
	/**
	 * メモ
	 */
	private WorkExpectationMemo memo;
	
	/**
	 * 勤務希望
	 */
	private WorkExpectation workExpectation;
	
	/*
	 * 作る
	 */
	public static WorkExpectationOfOneDay create(
			String employeeId,
			GeneralDate expectingDate,
			WorkExpectationMemo memo,
			AssignmentMethod assignmentMethod,
			List<ShiftMasterCode> shiftMasterCodeList,
			List<TimeSpanForCalc> timeZoneList
			) {
		
		WorkExpectation workExpectation;
		switch (assignmentMethod) {
		
			case SHIFT:
				workExpectation = ShiftExpectation.create(shiftMasterCodeList); break;
				
			case TIME_ZONE:
				workExpectation = TimeZoneExpectation.create(timeZoneList); break;
				
			case HOLIDAY: default:
				workExpectation = new HolidayExpectation(); break;
		}
		
		return new WorkExpectationOfOneDay(employeeId, expectingDate, memo, workExpectation);
	}
	
	/*
	 *  休日の勤務希望である	
	 */
	public boolean isHolidayExpectation() {
		return this.workExpectation.isHolidayExpectation();
	}
	
	/*
	 * 希望に沿っているか
	 */
	public boolean isMatchingExpectation(WorkExpectation.Require require, 
			WorkInformation workInformation, 
			List<TimeSpanForCalc> timeZoneList) {
		
		return this.workExpectation.isMatchingExpectation(require, workInformation, timeZoneList);
				
	}
	
	/*
	 * 表示情報を返す
	 */
	public WorkExpectDisplayInfoOfOneDay getDisplayInformation(WorkExpectation.Require require) {
		WorkExpectDisplayInfo displayInfo = this.workExpectation.getDisplayInformation(require);
		return new WorkExpectDisplayInfoOfOneDay(employeeId, expectingDate, memo, displayInfo);
	}

}
