package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported;

import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;


/**
 * «Imported» 社員の雇用期間
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.組織管理.社員情報.所属雇用履歴
 * @author HieuLt
 */
@Getter
@RequiredArgsConstructor
public class EmploymentPeriodImported {

	/**社員ID **/ 
	private final String empID; 
	/**期間 **/ 
	private final DatePeriod datePeriod;
	/**雇用コード **/
	private final String employmentCd;
	/**給与区分 **/
	private final Optional<SalarySegment> otpSalarySegment; 
	
}
