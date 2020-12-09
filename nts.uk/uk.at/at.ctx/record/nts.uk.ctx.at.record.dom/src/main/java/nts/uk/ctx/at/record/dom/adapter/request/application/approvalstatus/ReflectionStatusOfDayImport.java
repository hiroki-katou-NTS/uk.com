package nts.uk.ctx.at.record.dom.adapter.request.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

import java.util.Optional;

@Data
@AllArgsConstructor
public class ReflectionStatusOfDayImport {
	/**
	 * 実績反映状態
	 */
	private int actualReflectStatus;
	
	/**
	 * 予定反映状態
	 */
	private int scheReflectStatus;
	
	/**
	 * 対象日
	 */
	private GeneralDate targetDate;
	
	/**
	 * 申請反映の更新状態
	 */
	private Optional<DailyAttendanceUpdateStatusImport> opUpdateStatusAppReflect;
	
	/**
	 * 申請取消の更新状態
	 */
	private Optional<DailyAttendanceUpdateStatusImport> opUpdateStatusAppCancel;
	
}
