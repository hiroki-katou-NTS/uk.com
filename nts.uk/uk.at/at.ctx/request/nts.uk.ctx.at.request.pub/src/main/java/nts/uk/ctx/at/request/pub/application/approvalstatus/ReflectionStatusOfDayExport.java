package nts.uk.ctx.at.request.pub.application.approvalstatus;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class ReflectionStatusOfDayExport {
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
	private Optional<DailyAttendanceUpdateStatusExport> opUpdateStatusAppReflect;
	
	/**
	 * 申請取消の更新状態
	 */
	private Optional<DailyAttendanceUpdateStatusExport> opUpdateStatusAppCancel;
	
}
