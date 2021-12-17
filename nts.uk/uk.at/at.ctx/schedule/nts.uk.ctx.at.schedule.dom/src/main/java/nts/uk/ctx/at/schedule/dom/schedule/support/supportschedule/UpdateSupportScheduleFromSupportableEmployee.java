package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ResultOfRegisteringWorkSchedule;
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
	public RegisterResultFromSupportableEmployee add(Require require, SupportableEmployee supportableEmployee) {
		
		val addingTicket = supportableEmployee.toTickets();
		
		return updateSupportScheduleBySupportTicket(require, supportableEmployee, addingTicket, 
				UpdateSupportScheduleBySupportTicket::add );
		
	}
	
	/**
	 * 修正する
	 * @param require
	 * @param afterModify 修正後
	 * @return
	 */
	public RegisterResultFromSupportableEmployee modify(Require require, SupportableEmployee afterModify) {
		
		SupportableEmployee beforeModify = require.getSupportableEmployee(afterModify.getId()).get();
		List<SupportTicket> ticketsBeforeModify = beforeModify.toTickets();
		List<SupportTicket> ticketsAfterModify = afterModify.toTickets();
		
		// add
		val addingDates = afterModify.getPeriod().subtract(beforeModify.getPeriod()).stream()
				.map(DatePeriod::datesBetween)
				.flatMap(List::stream)
				.collect(Collectors.toList());
		val addingTickets = ticketsAfterModify.stream()
				.filter( ticket -> addingDates.contains(ticket.getDate()))
				.collect(Collectors.toList());
		val addingResult = updateSupportScheduleBySupportTicket(require, afterModify, addingTickets, 
				UpdateSupportScheduleBySupportTicket::add );
		if ( addingResult.isError() ) {
			return addingResult;
		}
		
		// remove
		val removingDates = beforeModify.getPeriod().subtract(afterModify.getPeriod()).stream()
				.map(DatePeriod::datesBetween)
				.flatMap(List::stream)
				.collect(Collectors.toList());
		val removingTicket = ticketsAfterModify.stream()
				.filter( ticket -> removingDates.contains(ticket.getDate()))
				.collect(Collectors.toList());
		val removingResult = updateSupportScheduleBySupportTicket(require, afterModify, removingTicket, 
				UpdateSupportScheduleBySupportTicket::remove );
		if ( removingResult.isError() ) {
			return removingResult;
		}
		
		// modify
		val modifyingResult = updateSupportScheduleByModifySupportTicket(require, afterModify, ticketsBeforeModify, ticketsAfterModify);
		if ( modifyingResult.isError() ) {
			return modifyingResult;
		}
		
		List<AtomTask> atomTaskList = new ArrayList<>();
		atomTaskList.addAll(addingResult.getAtomTaskList());
		atomTaskList.addAll(removingResult.getAtomTaskList());
		atomTaskList.addAll(modifyingResult.getAtomTaskList());
		
		return RegisterResultFromSupportableEmployee.createWithoutError(atomTaskList);
	}
	
	/**
	 * 削除する
	 * @param require
	 * @param supportableEmployee 応援可能な社員
	 * @return
	 */
	public RegisterResultFromSupportableEmployee remove(Require require, SupportableEmployee supportableEmployee) {
		
		val removingTicket = supportableEmployee.toTickets();
		return updateSupportScheduleBySupportTicket(require, supportableEmployee, removingTicket, 
				UpdateSupportScheduleBySupportTicket::remove );
	}
	
	/**
	 * 応援チケットで応援予定を変更する	
	 * @param require
	 * @param supportableEmployee 応援可能な社員
	 * @param supportTicketList 応援チケットリスト
	 * @param doSomething 応援予定変更処理
	 * @return
	 */
	private RegisterResultFromSupportableEmployee updateSupportScheduleBySupportTicket(
			Require require, SupportableEmployee supportableEmployee, 
			List<SupportTicket> supportTicketList, ISupportScheduleRegister processUpdateSupportSchedule) {
		
		List<AtomTask> atomTaskList = new ArrayList<>();
		
		for (SupportTicket ticket : supportTicketList) {
			
			boolean isExistWorkSchedule = require.isExistWorkSchedule(ticket.getEmployeeId().v(), ticket.getDate());
			
			if ( !isExistWorkSchedule ) {
				if ( ticket.getSupportType() == SupportType.ALLDAY ) {
					continue; 
				} else {
					return RegisterResultFromSupportableEmployee.createWithError(supportableEmployee, 
							new BusinessException("Msg_2274").getMessage());
				}
			}
			
			val registerResult = processUpdateSupportSchedule.apply(require, ticket);
			if ( registerResult.isHasError() ) {
				
				return RegisterResultFromSupportableEmployee.createWithError(supportableEmployee, 
						registerResult.getErrorInformation().get(0).getErrorMessage());
			}
			
			atomTaskList.add( registerResult.getAtomTask().get() );
		}
		
		
		return RegisterResultFromSupportableEmployee.createWithoutError(atomTaskList);
	}
	
	/**
	 * 修正の応援チケットで応援予定を変更する
	 * @param require
	 * @param supportableEmployee 応援可能な社員
	 * @param ticketsBeforeModify 修正前の応援チケットリスト
	 * @param ticketsAfterModify 修正後の応援チケットリスト
	 * @return
	 */
	private RegisterResultFromSupportableEmployee updateSupportScheduleByModifySupportTicket(
			Require require, SupportableEmployee supportableEmployee,
			List<SupportTicket> ticketsBeforeModify, List<SupportTicket> ticketsAfterModify
			) {
		
		List<AtomTask> atomTaskList = new ArrayList<>();
		
		for (SupportTicket ticketAfterModify : ticketsAfterModify) {
			
			Optional<SupportTicket> ticketBeforeModify = ticketsBeforeModify.stream()
					.filter( ticket -> ticket.getDate().equals(ticketAfterModify.getDate()) )
					.findFirst();
			if ( !ticketBeforeModify.isPresent() ) {
				continue;
			}
			
			boolean isExistWorkSchedule = require.isExistWorkSchedule(ticketAfterModify.getEmployeeId().v(), ticketAfterModify.getDate());
			
			if ( !isExistWorkSchedule ) {
				if ( ticketAfterModify.getSupportType() == SupportType.ALLDAY ) {
					continue; 
				} else {
					return RegisterResultFromSupportableEmployee.createWithError(supportableEmployee, 
							new BusinessException("Msg_2274").getMessage());
				}
			}
			
			val modifyResult = UpdateSupportScheduleBySupportTicket.modify(require, ticketBeforeModify.get(), ticketAfterModify);
			if ( !modifyResult.isPresent() ) {
				continue;
			}
			
			if ( modifyResult.get().isHasError() ) {
				return RegisterResultFromSupportableEmployee.createWithError(supportableEmployee, 
						modifyResult.get().getErrorInformation().get(0).getErrorMessage());
			}
			
			atomTaskList.add( modifyResult.get().getAtomTask().get() );
			
		}
		
		return RegisterResultFromSupportableEmployee.createWithoutError(atomTaskList);
	}
	
	@FunctionalInterface
	public static interface ISupportScheduleRegister {
		
		ResultOfRegisteringWorkSchedule apply(Require require, SupportTicket ticket);

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
