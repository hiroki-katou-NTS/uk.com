package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class AnnualHolidayPlan {
	/** 社員ID	 */
	private String sId;
	/** 勤務種類コード	 */
	private String workTypeCd;
	/** 年月日	 */
	private GeneralDate ymd;
	/** 年休使用	 */
	private Double maxDay;
}
