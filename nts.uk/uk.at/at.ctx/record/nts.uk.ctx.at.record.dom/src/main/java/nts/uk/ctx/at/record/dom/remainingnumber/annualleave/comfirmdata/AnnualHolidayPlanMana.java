package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.comfirmdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 計画年休管理データ
 * @author do_dt
 *
 */
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.ManagementDays;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnnualHolidayPlanMana extends AggregateRoot{
	/** 社員ID	 */
	private String sId;
	/** 勤務種類コード	 */
	private String workTypeCd;
	/** 年月日	 */
	private GeneralDate ymd;
	/** 年休使用	 */
	private ManagementDays useDays;
}
