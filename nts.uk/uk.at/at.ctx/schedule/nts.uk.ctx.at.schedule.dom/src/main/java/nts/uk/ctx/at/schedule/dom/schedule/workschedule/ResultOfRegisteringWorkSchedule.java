package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.Optional;

import lombok.Value;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;

@Value
public class ResultOfRegisteringWorkSchedule {
	
	private boolean hasError;
	
	private Optional<ErrorInfoOfWorkSchedule> errorInfomation;
	
	private Optional<AtomTask> atomTask;
	
	/**
	 * [C-1] 作る
	 * @param atomTask
	 * @return
	 */
	public static ResultOfRegisteringWorkSchedule create(AtomTask atomTask) {
		
		return new ResultOfRegisteringWorkSchedule(false, Optional.empty(), Optional.of(atomTask));
	}
	
	/**
	 * [C-2] エラーありで作る
	 * @param employeeId 社員ID
	 * @param date　年月日
	 * @param message　メッセージ
	 * @return
	 */
	public static ResultOfRegisteringWorkSchedule createWithError(String employeeId, GeneralDate date, String message) {
		// TODO itemName is null?
		ErrorInfoOfWorkSchedule errorInformation = new ErrorInfoOfWorkSchedule(employeeId, date, null, message);
		
		return new ResultOfRegisteringWorkSchedule(true, Optional.of(errorInformation), Optional.empty());
	}
	
	public static ResultOfRegisteringWorkSchedule createWithErrorList() {
		// TODO not done
		return null;
	}

}
