package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 基本的な設定
 */

@Getter
public class BasicSetting extends DomainObject {
	/**
	 * 経理締め日
	 */
	private AccountingClosureDate accountingClosureDate;

	/**
	 * 社員抽出基準日
	 */
	private EmployeeExtractionReferenceDate employeeExtractionReferenceDate;

	/**
	 * 毎月の支払日
	 */
	private MonthlyPaymentDate monthlyPaymentDate;

	/**
	 * 要勤務日数
	 */
	private ReqStandardWorkingDays workDay;

	public BasicSetting(int processMonth, int disposalDay, int refeMonth, int refeDate, int datePayMent,
			BigDecimal workDay) {
		super();
		this.accountingClosureDate = new AccountingClosureDate(processMonth, disposalDay);
		this.employeeExtractionReferenceDate = new EmployeeExtractionReferenceDate(refeMonth, refeDate);
		this.monthlyPaymentDate = new MonthlyPaymentDate(datePayMent);
		this.workDay = new ReqStandardWorkingDays(workDay);
	}
}
