package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreMaxTimeOfMonthly;

/**
 * Output：36協定月間上限時間
 * @author shuichi_ishida
 */
@Getter
public class AgreMaxTimeMonthOut {

	/** 年月 */
	private YearMonth yearMonth;
	/** 上限時間 */
	private AgreMaxTimeOfMonthly maxTime;
	
	/**
	 * コンストラクタ
	 */
	public AgreMaxTimeMonthOut() {
		this.yearMonth = GeneralDate.today().yearMonth();
		this.maxTime = new AgreMaxTimeOfMonthly();
	}

	/**
	 * ファクトリー
	 * @param yearMonth 年月
	 * @param maxTime 上限時間
	 * @return 36協定月間上限時間
	 */
	public static AgreMaxTimeMonthOut of(
			YearMonth yearMonth,
			AgreMaxTimeOfMonthly maxTime) {
		
		AgreMaxTimeMonthOut domain = new AgreMaxTimeMonthOut();
		domain.yearMonth = yearMonth;
		domain.maxTime = maxTime;
		return domain;
	}
}
