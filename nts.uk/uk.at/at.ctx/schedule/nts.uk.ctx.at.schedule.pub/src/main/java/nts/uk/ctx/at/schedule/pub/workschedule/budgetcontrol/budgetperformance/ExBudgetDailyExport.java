package nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class ExBudgetDailyExport {

	/**対象組織識別情報 **/
	private TargetOrgIdenInforExport targetOrg;

	/** 外部予算実績項目コード **/
	private String itemCode;

	/** 年月日 **/
	private GeneralDate ymd;

	/**	値- 外部予算実績値  **/
	private int actualValue;

}
