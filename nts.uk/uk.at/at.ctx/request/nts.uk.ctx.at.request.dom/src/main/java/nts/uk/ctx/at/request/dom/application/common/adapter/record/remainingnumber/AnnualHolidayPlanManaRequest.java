package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnnualHolidayPlanManaRequest {
	/** 社員ID	 */
	private String sId;
	/** 勤務種類コード	 */
	private String workTypeCd;
	/** 年月日	 */
	private GeneralDate ymd;
	/** 年休使用	 */
	private Double maxDay;
}
