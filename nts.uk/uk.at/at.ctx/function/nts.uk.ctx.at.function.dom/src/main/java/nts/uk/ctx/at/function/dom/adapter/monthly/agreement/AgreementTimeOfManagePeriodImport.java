package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * 管理期間の36協定時間
 * @author shuichu_ishida
 */
@Getter
public class AgreementTimeOfManagePeriodImport extends AggregateRoot {

	/** 社員ID */
	private String employeeId;
	/** 月度 */
	private YearMonth yearMonth;
	/** 年度 */
	private Year year;
	/** 36協定時間 */
	private AgreementTimeOfMonthlyImport agreementTime;
	/** 内訳 */
	private AgreementTimeBreakdownImport breakdown;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param yearMonth 月度
	 */
	public AgreementTimeOfManagePeriodImport(String employeeId, YearMonth yearMonth){
		
		super();
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.year = new Year(yearMonth.year());
		this.agreementTime = new AgreementTimeOfMonthlyImport();
		this.breakdown = new AgreementTimeBreakdownImport();
	}
	
	/**
	 * ファクトリー
	 * @param employeeId 社員ID
	 * @param yearMonth 月度
	 * @param year 年度
	 * @param agreementTime 36協定時間
	 * @param breakdown 内訳
	 * @return 管理期間の36協定時間
	 */
	public static AgreementTimeOfManagePeriodImport of(
			String employeeId,
			YearMonth yearMonth,
			Year year,
			AgreementTimeOfMonthlyImport agreementTime,
			AgreementTimeBreakdownImport breakdown){
	
		AgreementTimeOfManagePeriodImport domain = new AgreementTimeOfManagePeriodImport(employeeId, yearMonth);
		domain.year = year;
		domain.agreementTime = agreementTime;
		domain.breakdown = breakdown;
		return domain;
	}
}
