package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.gul.util.OptionalUtil;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportTicket;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo.ClockAreaAtr;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo.ContainsResult;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 勤務予定を作る
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定.勤務予定を作る
 * @author dan_pv
 */
public class CreateWorkSchedule {
	
	/**
	 * 作る
	 * @param require
	 * @param employeeId 社員ID
	 * @param date 年月日
	 * @param workInformation 勤務情報
	 * @param isUpdateBreakTimeList 休憩時間帯が手修正か
	 * @param breakTimeList 休憩時間帯
	 * @Param supportTicketList 応援チケットリスト
	 * @param updateInfoMap 変更する情報Map
	 * @return
	 */
	public static <T> ResultOfRegisteringWorkSchedule create(
			Require require, 
			String employeeId, 
			GeneralDate date, 
			WorkInformation workInformation,
			boolean isUpdateBreakTimeList,
			List<TimeSpanForCalc> breakTimeList,
			List<SupportTicket> supportTicketList,
			Map<Integer, T> updateInfoMap) {
		
		Optional<WorkSchedule> registedWorkSchedule = require.getWorkSchedule(employeeId, date);
		boolean isNewRegister = !registedWorkSchedule.isPresent();
		
		WorkSchedule workSchedule;
		if ( isNewRegister || ! registedWorkSchedule.get().getWorkInfo().getRecordInfo().isSame(workInformation) ) {
			try {
				workSchedule = WorkSchedule.createByHandCorrectionWithWorkInformation(require, employeeId, date, workInformation);
				workSchedule.createSupportSchedule(require, supportTicketList);
			} catch (BusinessException e) {
				return ResultOfRegisteringWorkSchedule.createWithError( employeeId, date, e.getMessage() );
			}
		} else {
			workSchedule = registedWorkSchedule.get();
		}
		
		// 時間帯チェック CheckTime Span
		List<ErrorInfoOfWorkSchedule> errorList = 
				CreateWorkSchedule.checkTimeSpan(require, employeeId, date, workInformation, updateInfoMap);
		if ( !errorList.isEmpty() ) {
			return ResultOfRegisteringWorkSchedule.createWithErrorList(errorList);
		}
		
		// update item
		workSchedule.changeAttendanceItemValueByHandCorrection(require, updateInfoMap);
		
		// update break time list
		if ( isUpdateBreakTimeList ) {
			workSchedule.handCorrectBreakTimeList(require, breakTimeList);
		}
		
		// check consistency of support schedule with work's start-end time, task's start-end time 
		try {
			workSchedule.checkConsistencyOfSupportSchedule(require);
		} catch ( BusinessException e ) {
			return ResultOfRegisteringWorkSchedule.createWithError( employeeId, date, e.getMessage() );
		}
		
		WorkSchedule correctedResult = require.correctWorkSchedule(workSchedule);
		
		AtomTask atomTask = AtomTask.of( () -> {
			if ( isNewRegister ) {
				require.insertWorkSchedule(correctedResult);
			} else {
				require.updateWorkSchedule(correctedResult);
			}
			require.registerTemporaryData(employeeId, date);
		});
		
		return ResultOfRegisteringWorkSchedule.create(atomTask);
		
	}
	
	/**
	 * 時間帯のチェック
	 * @param require
	 * @param employeeId 社員ID
	 * @param date 年月日
	 * @param workInformation 勤務情報
	 * @param updateInfoMap 変更する情報Map
	 * @return
	 */
	private static <T> List<ErrorInfoOfWorkSchedule> checkTimeSpan(
			Require require,
			String employeeId,
			GeneralDate date,
			WorkInformation workInformation,
			Map<Integer, T> updateInfoMap
			) {
		
		return Stream.of ( WorkTimeZone.values() )
					.map( workTimeZone -> getErrorInfoWithWorkTimeZone(require, employeeId, date, workInformation, workTimeZone, updateInfoMap) )
					.flatMap( OptionalUtil::stream )
					.collect( Collectors.toList() );
	}
	
	/**
	 * 勤務時間帯に対するエラー情報を作成する
	 * @param require
	 * @param employeeId 社員ID
	 * @param date 年月日
	 * @param workInformation 勤務情報
	 * @param workTimeZone 勤務時間帯
	 * @param updateInfoMap 変更する情報Map
	 * @return
	 */
	private static <T> Optional<ErrorInfoOfWorkSchedule> getErrorInfoWithWorkTimeZone(
			Require require,
			String employeeId, 
			GeneralDate date, 
			WorkInformation workInformation, 
			WorkTimeZone workTimeZone,
			Map<Integer, T> updateInfoMap
			) {
		
		if ( !updateInfoMap.containsKey(workTimeZone.attendanceItemId) ) {
			return Optional.empty();
		}
		
		T time = updateInfoMap.get( workTimeZone.attendanceItemId );
		ContainsResult stateOfTime = 
				workInformation.containsOnChangeableWorkingTime(require, workTimeZone.clockArea , workTimeZone.workNo, (TimeWithDayAttr) time);
		if ( stateOfTime.isContains() ) {
			return Optional.empty();
		}
		
		String errorMessage = new BusinessException(
					"Msg_1781", 
					stateOfTime.getTimeSpan().get().getStart().getInDayTimeWithFormat(), 
					stateOfTime.getTimeSpan().get().getEnd().getInDayTimeWithFormat()
				).getMessage();
		
		return Optional.of(
				ErrorInfoOfWorkSchedule.attendanceItemError(employeeId, date, workTimeZone.attendanceItemId, errorMessage));
	}
	
	public static interface Require extends WorkSchedule.Require{
		
		/**
		 * 勤務予定を取得する
		 * @param employeeId 社員ID
		 * @param date 年月日
		 * @return
		 */
		Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date);
		
		/**
		 * 勤務予定を補正する
		 * @param workSchedule 勤務予定
		 */
		WorkSchedule correctWorkSchedule(WorkSchedule workSchedule);
		
		/**
		 * 勤務予定を新規登録する
		 * @param workSchedule 勤務予定
		 */
		void insertWorkSchedule(WorkSchedule workSchedule);
		
		/**
		 * 勤務予定を更新する
		 * @param workSchedule 勤務予定
		 */
		void updateWorkSchedule(WorkSchedule workSchedule);
		
		/**
		 * 暫定データを登録する
		 * @param employeeId 社員ID
		 * @param date 年月日
		 */
		void registerTemporaryData(String employeeId, GeneralDate date);
	}
	
	@RequiredArgsConstructor
	static enum WorkTimeZone {
		
		// 開始時刻１
		START_TIME_1( WS_AttendanceItem.StartTime1.ID, ClockAreaAtr.START, new WorkNo(1)),

		// 終了時刻１
		END_TIME_1( WS_AttendanceItem.EndTime1.ID, ClockAreaAtr.END, new WorkNo(1) ),
		
		// 開始時刻２ 
		START_TIME_2 ( WS_AttendanceItem.StartTime2.ID, ClockAreaAtr.START, new WorkNo(2) ),
		
		// 終了時刻２
		END_TIME_2( WS_AttendanceItem.EndTime2.ID, ClockAreaAtr.END, new WorkNo(2) );
		
		public final int attendanceItemId;
		
		public final ClockAreaAtr clockArea;
		
		public final WorkNo workNo;

	}

}
