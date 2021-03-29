package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.Optional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;

/**
 * 確定状態を変更する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定.確定状態を変更する
 * @author lan_lt
 *
 */
public class ChangeConfirmedService {
	/**
	 * 変更する
	 * @param require Require
	 * @param sid 社員ID
	 * @param ymd 年月日
	 * @param isConfimred 確定か
	 * @return
	 */
	public static AtomTask change(Require require, String sid, GeneralDate ymd, boolean isConfimred) {
		
		Optional<WorkSchedule> workScheduleOpt = require.getWorkSchedule(sid, ymd);
		if(!workScheduleOpt.isPresent()) {
			throw new BusinessException("Msg_1541");
		}
		
		val workSchedule = workScheduleOpt.get();
		if(isConfimred) {
			workSchedule.confirm();
		}else {
			workSchedule.removeConfirm();
		}
		
		return AtomTask.of(() -> require.update(workSchedule));
	}
	
	
	public static interface Require {
		 /**
		  * 勤務予定を取得する
		  * @param sid 社員ID
		  * @param ymd 年月日
		  * @return
		  */
		Optional<WorkSchedule> getWorkSchedule(String sid, GeneralDate ymd);
		
		/**
		 * 勤務予定を更新する
		 * @param workSchedule 勤務予定
		 */
		void update(WorkSchedule workSchedule);
	}

}
