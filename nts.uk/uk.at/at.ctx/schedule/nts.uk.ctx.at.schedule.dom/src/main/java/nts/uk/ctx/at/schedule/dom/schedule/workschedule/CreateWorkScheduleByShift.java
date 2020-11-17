package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;

public class CreateWorkScheduleByShift {
	
	public static ResultOfRegisteringWorkSchedule create(Require require, String employeeId, GeneralDate date, ShiftMasterCode shiftMasterCode) {
		Optional<ShiftMaster> shiftMaster = require.getShiftMaster(shiftMasterCode);
		
		if (! shiftMaster.isPresent()) {
			return ResultOfRegisteringWorkSchedule.createWithError(employeeId, date, "Msg_1705");
		}
		
		Map<Integer, String> updateInformation = new HashMap<>();
		updateInformation.put(1, shiftMaster.get().getWorkTypeCode().v());
		updateInformation.put(2, shiftMaster.get().getWorkTimeCode().v());
		
		// TODO to be continue
		return null;
	}
	
	public static interface Require {
		
		/**
		 * シフトマスタを取得する
		 * @param shiftMasterCode シフトマスタコード
		 * @return
		 */
		Optional<ShiftMaster> getShiftMaster(ShiftMasterCode shiftMasterCode);
		
	}

}
