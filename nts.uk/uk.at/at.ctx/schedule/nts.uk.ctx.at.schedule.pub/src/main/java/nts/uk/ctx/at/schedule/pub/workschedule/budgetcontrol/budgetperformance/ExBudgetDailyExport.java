package nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
public class ExBudgetDailyExport {

	/**対象組織識別情報 **/
	@Getter
	private TargetOrgIdenInforExport targetOrg;

	/** 外部予算実績項目コード **/
	@Getter
	private String itemCode;

	/** 年月日 **/
	@Getter
	private GeneralDate ymd;

	/**	値- 外部予算実績値  **/
	@Getter
	private int actualValue;

	
	
}
