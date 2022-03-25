package nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 作業予定を一括付与する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.作業.作業予定.作業予定を一括付与する
 * @author dan_pv
 */
public class GrantListOfTaskSchedule {
	
	/**
	 * 付与する
	 * @param require
	 * @param employeeIds 社員リスト
	 * @param date 年月日
	 * @param taskCode 作業コード
	 * @param timeSpan 対象時間帯
	 * @return
	 */
	public static List<AtomTask> grant(Require require, List<String> employeeIds, GeneralDate date, 
			TaskCode taskCode, Optional<TimeSpanForCalc> timeSpan) {
		
		String companyId = AppContexts.user().companyId();
		
		List<WorkSchedule> listWorkSchedule = require.getWorkSchedule(employeeIds, date);
		
		boolean existTimeSpanSupportType = listWorkSchedule.stream().anyMatch( workSchedule -> {

					val supportType = workSchedule.getSupportSchedule().getSupportType();
					if ( supportType.isPresent() && supportType.get() == SupportType.TIMEZONE) {
						return true;
					}
					
					return false;
				});
		
		if ( existTimeSpanSupportType ) {
			throw new BusinessException("Msg_3234");
		}
		
		return listWorkSchedule.stream().map( workSchedule -> {
			
			if ( !timeSpan.isPresent() ) {
				workSchedule.createTaskScheduleForWholeDay(require, companyId, taskCode);
			} else {
				workSchedule.addTaskScheduleWithTimeSpan(require, companyId, timeSpan.get(), taskCode);
			}
			
			return AtomTask.of(() -> require.updateWorkSchedule(workSchedule));
		}).collect(Collectors.toList());
		
	}
	
	public static interface Require extends WorkSchedule.Require {
		
		/**
		 * 勤務予定を取得する
		 * @param employeeIds 社員リスト
		 * @param date 年月日
		 * @return
		 */
		List<WorkSchedule> getWorkSchedule(List<String> employeeIds, GeneralDate date);
		
		/**
		 * 勤務予定を更新する
		 * @param workSchedule 勤務予定
		 */
		void updateWorkSchedule(WorkSchedule workSchedule);
	}

}
