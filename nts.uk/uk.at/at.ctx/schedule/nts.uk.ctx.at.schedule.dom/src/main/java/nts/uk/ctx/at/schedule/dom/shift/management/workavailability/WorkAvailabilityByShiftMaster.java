package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * シフトの勤務希望
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.勤務希望
 * @author dan_pv
 *
 */
@Value
public class WorkAvailabilityByShiftMaster implements WorkAvailability, DomainValue  {

	/**
	 * 勤務可能なシフトのリスト
	 */
	private List<ShiftMasterCode> workableShiftCodeList;

	/**
	 * 作る
	 * @param workableShiftCodeList 社員の勤務希望シフトリスト
	 * @return
	 */
	public static WorkAvailabilityByShiftMaster create(List<ShiftMasterCode> workableShiftCodeList) {

		if (workableShiftCodeList.isEmpty()) {
			throw new RuntimeException("workable shift code list is empty!");
		}

		return new WorkAvailabilityByShiftMaster(workableShiftCodeList);
	}

	@Override
	public AssignmentMethod getAssignmentMethod() {
		return AssignmentMethod.SHIFT;
	}

	@Override
	public boolean isMatchingWorkAvailability(WorkAvailability.Require require, WorkInformation workInformation,
			List<TimeSpanForCalc> timeZoneList) {
		
		Optional<ShiftMaster> shiftMaster = require.getShiftMasterByWorkInformation(
				workInformation.getWorkTypeCode(),
				workInformation.getWorkTimeCode());
		
		if( !shiftMaster.isPresent()) {
			return false;
		}
		
		return this.workableShiftCodeList.stream()
				.anyMatch( code -> code.equals(shiftMaster.get().getShiftMasterCode()));
	}

	@Override
	public WorkAvailabilityDisplayInfo getDisplayInformation(WorkAvailability.Require require) {
		List<String> shiftMasterNameList = require.getShiftMaster(this.workableShiftCodeList)
												.stream().map(shiftmaster -> shiftmaster.getDisplayInfor().getName().v())
												.collect(Collectors.toList());
		
		AssignmentMethod asignmentMethod = this.getAssignmentMethod();
		return new WorkAvailabilityDisplayInfo(asignmentMethod, shiftMasterNameList, Collections.emptyList());
	}
	
	public static interface Require {
		
		// 勤務情報からシフトマスタを取得する
		Optional<ShiftMaster> getShiftMasterByWorkInformation(WorkTypeCode workTypeCode, WorkTimeCode workTimeCode);

		// シフトマスタを取得する
		List<ShiftMaster> getShiftMaster(List<ShiftMasterCode> shiftMasterCodeList);
		
	}
	
}
