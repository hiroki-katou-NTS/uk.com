package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
@Getter
@AllArgsConstructor
public class ExtBudgetDailyImport {
	/**対象組織識別情報 **/
	private TargetOrgIdenInfor targetOrg;
	/** 外部予算実績項目コード **/
	private String itemCode;
	/** 年月日 **/
	private GeneralDate ymd;
	/**	値- 外部予算実績値  **/
	private BigDecimal actualValue;
}
