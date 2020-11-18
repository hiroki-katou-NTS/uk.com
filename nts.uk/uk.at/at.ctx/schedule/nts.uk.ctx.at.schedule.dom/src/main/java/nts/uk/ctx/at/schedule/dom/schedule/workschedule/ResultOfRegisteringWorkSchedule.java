package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;

/**
 * 勤務予定の登録処理結果
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定.勤務予定の登録処理結果
 * @author dan_pv
 */
@Value
public class ResultOfRegisteringWorkSchedule {
	
	/** エラーがあるか */
	private boolean hasError;
	
	/** エラー情報 */
	private List<ErrorInfoOfWorkSchedule> errorInfomation;
	
	/** AtomTask */
	private Optional<AtomTask> atomTask;
	
	/**
	 * [C-1] 作る
	 * @param atomTask
	 * @return
	 */
	public static ResultOfRegisteringWorkSchedule create(AtomTask atomTask) {
		
		return new ResultOfRegisteringWorkSchedule(false, Collections.emptyList(), Optional.of(atomTask));
	}
	
	/**
	 * [C-2] エラーありで作る
	 * @param employeeId 社員ID
	 * @param date　年月日
	 * @param message　メッセージ
	 * @return
	 */
	public static ResultOfRegisteringWorkSchedule createWithError(String employeeId, GeneralDate date, String message) {

		ErrorInfoOfWorkSchedule errorInformation = ErrorInfoOfWorkSchedule.preConditionError(employeeId, date, message);
		
		return new ResultOfRegisteringWorkSchedule(true, Arrays.asList(errorInformation), Optional.empty());
	}
	
	/**
	 * 複数エラーありで作る
	 * @param errorInfoList エラー情報
	 * @return
	 */
	public static ResultOfRegisteringWorkSchedule createWithErrorList(List<ErrorInfoOfWorkSchedule> errorInfoList) {

		return new ResultOfRegisteringWorkSchedule(true, errorInfoList, Optional.empty());
	}

}
