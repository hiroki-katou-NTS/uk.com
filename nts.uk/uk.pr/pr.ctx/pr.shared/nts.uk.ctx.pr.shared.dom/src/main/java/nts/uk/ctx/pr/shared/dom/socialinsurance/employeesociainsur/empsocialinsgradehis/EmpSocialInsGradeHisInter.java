package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.Value;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Value
public class EmpSocialInsGradeHisInter {
	
	private EmpSocialInsGradeHis history;
	
	private EmpSocialInsGradeInfo info;
	
	private YearMonthHistoryItem item;
}
