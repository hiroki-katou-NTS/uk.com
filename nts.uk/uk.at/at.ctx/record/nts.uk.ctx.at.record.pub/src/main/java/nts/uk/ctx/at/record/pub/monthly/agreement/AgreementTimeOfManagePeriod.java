package nts.uk.ctx.at.record.pub.monthly.agreement;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * 管理期間の36協定時間
 * @author shuichu_ishida
 */
@Getter
public class AgreementTimeOfManagePeriod extends AggregateRoot {

	/** 社員ID */
	private String employeeId;
	/** 月度 */
	private YearMonth yearMonth;
	/** 年度 */
	private Year year;
	/** 36協定時間 */
	private AgreementTimeOfMonthly agreementTime;
	/** 内訳 */
	private AgreementTimeBreakdown breakdown;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param yearMonth 月度
	 */
	public AgreementTimeOfManagePeriod(String employeeId, YearMonth yearMonth){
		
		super();
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.year = new Year(yearMonth.year());
		this.agreementTime = new AgreementTimeOfMonthly();
		this.breakdown = new AgreementTimeBreakdown();
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
	public static AgreementTimeOfManagePeriod of(
			String employeeId,
			YearMonth yearMonth,
			Year year,
			AgreementTimeOfMonthly agreementTime,
			AgreementTimeBreakdown breakdown){
	
		AgreementTimeOfManagePeriod domain = new AgreementTimeOfManagePeriod(employeeId, yearMonth);
		domain.year = year;
		domain.agreementTime = agreementTime;
		domain.breakdown = breakdown;
		return domain;
	}
}
