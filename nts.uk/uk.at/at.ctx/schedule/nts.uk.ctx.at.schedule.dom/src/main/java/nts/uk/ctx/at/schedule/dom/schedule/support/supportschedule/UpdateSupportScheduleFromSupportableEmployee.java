package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.UpdateSupportScheduleBySupportTicket;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportTicket;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployee;

/**
 * 応援可能な社員から応援予定を変更する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.応援.応援予定.応援可能な社員から応援予定を変更する
 * @author dan_pv
 */
public class UpdateSupportScheduleFromSupportableEmployee {
	
	/**
	 * 追加する
	 * @param require
	 * @param supportableEmployee 応援可能な社員
	 * @return
	 */
	public static RegisterResultFromSupportableEmployee add(Require require, SupportableEmployee supportableEmployee) {
		
		val addingTickets = supportableEmployee.toTickets();
		
		List<AtomTask> atomTaskList = new ArrayList<>();
		for (SupportTicket addingTicket : addingTickets) {
			
			val addingResult = updateSupportScheduleByAddSupportTicket(require, supportableEmployee, addingTicket);
			if ( addingResult.isError() ) {
				return addingResult;
			}
			
			atomTaskList.addAll( addingResult.getAtomTaskList() );
		}
		
		return RegisterResultFromSupportableEmployee.createWithoutError(atomTaskList);
		
	}
	
	/**
	 * 修正する
	 * @param require
	 * @param afterModify 修正後
	 * @return
	 */
	public static RegisterResultFromSupportableEmployee modify(Require require, SupportableEmployee afterModify) {
		
		SupportableEmployee beforeModify = require.getSupportableEmployee(afterModify.getId()).get();
		
		List<AtomTask> atomTaskList = new ArrayList<>();
		val dates = beforeModify.getPeriod().join(afterModify.getPeriod()).datesBetween();
		
		for (GeneralDate date : dates) {
			Action action = decideAction(beforeModify.getPeriod(), afterModify.getPeriod(), date);
			
			switch (action) {
			case ADD:
				
				val addTicket = afterModify.createTicket(date).get();
				
				val addingResult = updateSupportScheduleByAddSupportTicket(require, afterModify, addTicket);
				if ( addingResult.isError() ) {
					return addingResult;
				}
				
				atomTaskList.addAll( addingResult.getAtomTaskList() );
				
				break;
			case MODIFY:
				
				val ticketAfterModify = afterModify.createTicket(date).get();
				val ticketBeforeModify = beforeModify.createTicket(date).get();
				
				val modifyingResult = updateSupportScheduleByModifySupportTicket(require, afterModify, ticketBeforeModify, ticketAfterModify);
				if ( modifyingResult.isError() ) {
					return modifyingResult;
				}
				atomTaskList.addAll( modifyingResult.getAtomTaskList() );
				
				break;
			case REMOVE:
				
				val removeTicket = beforeModify.createTicket(date).get();
				
				val removingResult = updateSupportScheduleByRemoveSupportTicket(require, afterModify, removeTicket);
				if ( removingResult.isError() ) {
					return removingResult;
				}
				
				atomTaskList.addAll( removingResult.getAtomTaskList() );
				
				break;
			case DO_NOTHING:
			default:
				break;
			}
		}
		
		return RegisterResultFromSupportableEmployee.createWithoutError(atomTaskList);
	}
	
	/**
	 * 削除する
	 * @param require
	 * @param supportableEmployee 応援可能な社員
	 * @return
	 */
	public static RegisterResultFromSupportableEmployee remove(Require require, SupportableEmployee supportableEmployee) {
		
		val removingTickets = supportableEmployee.toTickets();
		
		List<AtomTask> atomTaskList = new ArrayList<>();
		for (SupportTicket removingTicket : removingTickets) {
			
			val removingResult = updateSupportScheduleByRemoveSupportTicket(require, supportableEmployee, removingTicket);
			if ( removingResult.isError() ) {
				return removingResult;
			}
			
			atomTaskList.addAll( removingResult.getAtomTaskList() );
		}
		
		return RegisterResultFromSupportableEmployee.createWithoutError(atomTaskList);
	}
	
	/**
	 * 追加の応援チケットで応援予定を変更する
	 * @param require
	 * @param supportableEmployee 応援可能な社員
	 * @param addingTicket 追加応援チケット
	 * @return
	 */
	private static RegisterResultFromSupportableEmployee updateSupportScheduleByAddSupportTicket(
			Require require, SupportableEmployee supportableEmployee, 
			SupportTicket addingTicket) {
			
		boolean isExistWorkSchedule = require.isExistWorkSchedule(addingTicket.getEmployeeId().v(), addingTicket.getDate());
		
		if ( !isExistWorkSchedule ) {
			if ( addingTicket.getSupportType() == SupportType.ALLDAY ) {
				return RegisterResultFromSupportableEmployee.createEmpty();
			} else {
				return RegisterResultFromSupportableEmployee.createWithError(supportableEmployee, 
						new BusinessException("Msg_2274").getMessage());
			}
		}
		
		val registerResult = UpdateSupportScheduleBySupportTicket.add(require, addingTicket);
		if ( registerResult.isHasError() ) {
			
			return RegisterResultFromSupportableEmployee.createWithError(supportableEmployee, 
					registerResult.getErrorInformation().get(0).getErrorMessage());
		}
		
		return RegisterResultFromSupportableEmployee.createWithoutError( Arrays.asList(registerResult.getAtomTask().get()) );
	}
	
	/**
	 * 修正の応援チケットで応援予定を変更する
	 * @param require
	 * @param supportableEmployee 応援可能な社員
	 * @param ticketBeforeModify 修正前の応援チケット
	 * @param ticketAfterModify 修正後の応援チケット
	 * @return
	 */
	private static RegisterResultFromSupportableEmployee updateSupportScheduleByModifySupportTicket(
			Require require, SupportableEmployee supportableEmployee, 
			SupportTicket ticketBeforeModify,
			SupportTicket ticketAfterModify) {
			
		boolean isExistModifyingWorkSchedule = require.isExistWorkSchedule(ticketAfterModify.getEmployeeId().v(), ticketAfterModify.getDate());
		
		if ( !isExistModifyingWorkSchedule ) {
			if ( ticketAfterModify.getSupportType() == SupportType.ALLDAY ) {
				return RegisterResultFromSupportableEmployee.createEmpty();
			} else {
				return RegisterResultFromSupportableEmployee.createWithError(supportableEmployee, 
						new BusinessException("Msg_2274").getMessage());
			}
		}
		
		val modifyingResult = UpdateSupportScheduleBySupportTicket.modify(require, ticketBeforeModify, ticketAfterModify);
		if ( !modifyingResult.isPresent() ) {
			return RegisterResultFromSupportableEmployee.createEmpty();
		}
		
		if ( modifyingResult.get().isHasError() ) {
			return RegisterResultFromSupportableEmployee.createWithError(supportableEmployee, 
					modifyingResult.get().getErrorInformation().get(0).getErrorMessage());
		}
		
		return RegisterResultFromSupportableEmployee.createWithoutError( Arrays.asList(modifyingResult.get().getAtomTask().get()) );
	}

	/**
	 * 削除の応援チケットで応援予定を変更する
	 * @param require
	 * @param supportableEmployee 応援可能な社員
	 * @param removingTicket 削除応援チケット
	 * @return
	 */
	private static RegisterResultFromSupportableEmployee updateSupportScheduleByRemoveSupportTicket(
			Require require, SupportableEmployee supportableEmployee, 
			SupportTicket removingTicket) {
			
		boolean isExistWorkSchedule = require.isExistWorkSchedule(removingTicket.getEmployeeId().v(), removingTicket.getDate());
		
		if ( !isExistWorkSchedule ) {
			if ( removingTicket.getSupportType() == SupportType.ALLDAY ) {
				return RegisterResultFromSupportableEmployee.createEmpty();
			} else {
				return RegisterResultFromSupportableEmployee.createWithError(supportableEmployee, 
						new BusinessException("Msg_2274").getMessage());
			}
		}
		
		val registerResult = UpdateSupportScheduleBySupportTicket.remove(require, removingTicket);
		if ( registerResult.isHasError() ) {
			
			return RegisterResultFromSupportableEmployee.createWithError(supportableEmployee, 
					registerResult.getErrorInformation().get(0).getErrorMessage());
		}
		
		return RegisterResultFromSupportableEmployee.createWithoutError( Arrays.asList(registerResult.getAtomTask().get()) );
	}
	
	/**
	 * 操作を判定する
	 * @param beforeModify 修正前
	 * @param afterModify 修正後
	 * @param date 年月日
	 * @return
	 */
	private static Action decideAction(DatePeriod beforeModify, DatePeriod afterModify, GeneralDate date) {
		
		if ( beforeModify.contains(date) ) {
			if ( afterModify.contains(date) ) {
				return Action.MODIFY;
			} else {
				return Action.REMOVE;
			}
		} else {
			if ( afterModify.contains(date)) {
				return Action.ADD;
			} else {
				return Action.DO_NOTHING;
			} 
		}
		
	}
	
	private static enum Action {
		/** 追加 */
		ADD,
		/** 修正 */
		MODIFY,
		/** 削除 */
		REMOVE,
		/** しない */
		DO_NOTHING
	}
	
	public static interface Require extends UpdateSupportScheduleBySupportTicket.Require {
		
		/**
		 * 応援可能な社員を取得する
		 * @param id 応援可能な社員ID
		 * @return
		 */
		Optional<SupportableEmployee> getSupportableEmployee(String id);
		
		/**
		 * 勤務予定が登録されているか
		 * @param employeeId 社員ID
		 * @param date 年月日
		 * @return
		 */
		boolean isExistWorkSchedule(String employeeId, GeneralDate date); 
	}

}
