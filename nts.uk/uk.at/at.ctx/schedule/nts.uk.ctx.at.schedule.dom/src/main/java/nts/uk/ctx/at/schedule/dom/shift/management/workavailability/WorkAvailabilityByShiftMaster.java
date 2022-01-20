package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.gul.collection.CollectionUtil;
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
	 * @param require
	 * @param workableShiftCodeList 社員の勤務希望シフトリスト
	 * @return
	 */
	public static WorkAvailabilityByShiftMaster create(Require require, List<ShiftMasterCode> workableShiftCodeList) {

		if (workableShiftCodeList.isEmpty()) {
			throw new RuntimeException("workable shift code list is empty!");
		}
		
		if(!workableShiftCodeList.stream().allMatch(s -> require.shiftMasterIsExist(s))) {
			throw new BusinessException("Msg_1705");
		}

		return new WorkAvailabilityByShiftMaster(workableShiftCodeList);
	}

	@Override
	public boolean isHolidayAvailability(WorkAvailability.Require require, String companyId) {
		List<ShiftMaster> shiftList = require.getShiftMaster(this.workableShiftCodeList);
		return shiftList.stream()
				.anyMatch(c -> !c.isAttendanceRate(require, companyId));
	}

	@Override
	public AssignmentMethod getAssignmentMethod() {
		return AssignmentMethod.SHIFT;
	}

	@Override
	public boolean isMatchingWorkAvailability(
			WorkAvailability.Require require,
			String companyId,
			WorkInformation workInformation,
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
		
		Map<ShiftMasterCode, Optional<String>> shiftList = this.workableShiftCodeList.stream().collect(Collectors.toMap(c -> c, c -> {
			List<ShiftMaster> shiftMasterList = require.getShiftMaster(Arrays.asList(c));
			
			if(CollectionUtil.isEmpty(shiftMasterList)) 
				return Optional.empty();
			
			return Optional.ofNullable(shiftMasterList.get(0).getDisplayInfor().getName().v());
			
		}));
		
		AssignmentMethod asignmentMethod = this.getAssignmentMethod();
		return new WorkAvailabilityDisplayInfo(asignmentMethod, shiftList, Collections.emptyList());
	}
	
	public static interface Require {
		
		// 勤務情報からシフトマスタを取得する
		Optional<ShiftMaster> getShiftMasterByWorkInformation(WorkTypeCode workTypeCode, WorkTimeCode workTimeCode);

		// シフトマスタを取得する
		List<ShiftMaster> getShiftMaster(List<ShiftMasterCode> shiftMasterCodeList);
		
		//シフトマスタが存在するか
		boolean shiftMasterIsExist(ShiftMasterCode shiftMasterCode);
		
	}
}
