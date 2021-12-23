package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.Optional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportTicket;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定.応援チケットで応援予定を変更する
 * 応援チケットで応援予定を変更する
 * @author dan_pv
 *
 */
public class UpdateSupportScheduleBySupportTicket {
	
	/**
	 * 追加する
	 * @param require
	 * @param ticket 応援チケット
	 * @return
	 */
	public static ResultOfRegisteringWorkSchedule add(Require require, SupportTicket ticket) {
		
		WorkSchedule workSchedule = require.getWorkSchedule(ticket.getEmployeeId().v(), ticket.getDate()).get();
		
		try {
			workSchedule.addSupportSchedule(require, ticket);
		} catch (BusinessException e) {
			
			return ResultOfRegisteringWorkSchedule.createWithError(
					workSchedule.getEmployeeID(), workSchedule.getYmd(), e.getMessage());
		}
		
		val atomTask = AtomTask.of(() -> require.updateWorkSchedule(workSchedule));
		return ResultOfRegisteringWorkSchedule.create(atomTask);
	}
	
	/**
	 * 修正する
	 * @param require
	 * @param beforeModify 修正前
	 * @param afterModify 修正後
	 * @return
	 */
	public static Optional<ResultOfRegisteringWorkSchedule> modify(Require require, SupportTicket beforeModify, SupportTicket afterModify) {
	
		if ( beforeModify.equals(afterModify) ) {
			return Optional.empty();
		}
		
		WorkSchedule workSchedule = require.getWorkSchedule(afterModify.getEmployeeId().v(), afterModify.getDate()).get();
		
		try {
			workSchedule.modifySupportSchedule(require, beforeModify, afterModify);
		} catch (BusinessException e) {
			
			return Optional.of( ResultOfRegisteringWorkSchedule.createWithError(
					workSchedule.getEmployeeID(), workSchedule.getYmd(), e.getMessage()) );
		}
		
		val atomTask = AtomTask.of( () -> require.updateWorkSchedule(workSchedule) );
		return Optional.of( ResultOfRegisteringWorkSchedule.create(atomTask) );
	}
	
	/**
	 * 削除する
	 * @param require
	 * @param ticket 応援チケット
	 * @return
	 */
	public static ResultOfRegisteringWorkSchedule remove(Require require, SupportTicket ticket) {
		
		WorkSchedule workSchedule = require.getWorkSchedule(ticket.getEmployeeId().v(), ticket.getDate()).get();
		
		try {
			workSchedule.removeSupportSchedule(ticket);
		} catch (BusinessException e) {
			
			return ResultOfRegisteringWorkSchedule.createWithError(
					workSchedule.getEmployeeID(), workSchedule.getYmd(), e.getMessage());
		}
		
		val atomTask = AtomTask.of( () -> require.updateWorkSchedule(workSchedule) );
		return ResultOfRegisteringWorkSchedule.create(atomTask);
	}
	
	public static interface Require extends WorkSchedule.Require {
		
		/**
		 * 勤務予定を取得する
		 * @param employeeId 社員ID
		 * @param date 年月日
		 * @return
		 */
		Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date);
		
		/**
		 * 勤務予定を変更する
		 * @param workSchedule 勤務予定
		 */
		void updateWorkSchedule(WorkSchedule workSchedule);
	}

}
