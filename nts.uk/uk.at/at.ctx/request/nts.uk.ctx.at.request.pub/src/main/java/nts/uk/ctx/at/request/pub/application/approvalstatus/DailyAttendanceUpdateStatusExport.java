package nts.uk.ctx.at.request.pub.application.approvalstatus;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDateTime;

@Data
@AllArgsConstructor
public class DailyAttendanceUpdateStatusExport {
	/**
	 * 実績反映日時
	 */
	private Optional<GeneralDateTime> opActualReflectDateTime;
	
	/**
	 * 予定反映日時
	 */
	private Optional<GeneralDateTime> opScheReflectDateTime;
	
	/**
	 * 実績反映不可理由
	 */
	private Integer opReasonActualCantReflect;
	
	/**
	 * 予定反映不可理由
	 */
	private Integer opReasonScheCantReflect;

}
