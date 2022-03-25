package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;

/**
 * シフトで勤務予定を作成する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定.シフトで勤務予定を作成する
 * @author dan_pv
 *
 */
public class CreateWorkScheduleByShift {
	
	/**
	 * 作る
	 * @param require
	 * @param employeeId 社員ID
	 * @param date 年月日
	 * @param shiftMasterCode シフトマスタコード
	 * @return
	 */
	public static ResultOfRegisteringWorkSchedule create(Require require, String companyId, String employeeId, GeneralDate date, ShiftMasterCode shiftMasterCode) {
		Optional<ShiftMaster> shiftMaster = require.getShiftMaster(shiftMasterCode);
		
		if (! shiftMaster.isPresent()) {
			
			return ResultOfRegisteringWorkSchedule.createWithError(
					employeeId, 
					date, 
					new BusinessException("Msg_1705").getMessage() );
		}
		
		return CreateWorkSchedule.create(require, companyId, employeeId, date, shiftMaster.get(), false, new ArrayList<>(), new HashMap<>());
	}
	
	public static interface Require extends CreateWorkSchedule.Require {
		
		/**
		 * シフトマスタを取得する
		 * @param shiftMasterCode シフトマスタコード
		 * @return
		 */
		Optional<ShiftMaster> getShiftMaster(ShiftMasterCode shiftMasterCode);
		
	}

}
