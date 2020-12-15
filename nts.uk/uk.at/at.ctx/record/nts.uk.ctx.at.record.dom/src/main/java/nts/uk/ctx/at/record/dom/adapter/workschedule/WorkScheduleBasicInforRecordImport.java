package nts.uk.ctx.at.record.dom.adapter.workschedule;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkScheduleBasicInforRecordImport {
	/** 社員ID(employeeID) */
	private String employeeID;

	/** 社員ID(年月日(YMD) */
	private GeneralDate ymd;
	/**勤務種類コード	 */
	private String workTypeCd;
	/**就業時間帯コード	 */
	private Optional<String> workTimeCd;
}
