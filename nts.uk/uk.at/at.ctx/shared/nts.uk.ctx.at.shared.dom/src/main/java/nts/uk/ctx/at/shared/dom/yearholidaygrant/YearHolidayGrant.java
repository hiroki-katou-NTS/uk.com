package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import lombok.Getter;

/**
 * 年休付与テーブル
 * 
 * @author TanLV
 *
 */

@Getter
public class YearHolidayGrant {
	/* 会社ID */
	private String companyId;
	
	/* 年休付与NO */
	private int yearHolidayGrantNo;
	
	/* 条件NO */
	private int conditionNo;
	
	/* 年休付与テーブル設定コード */
	private YearHolidayCode yearHolidayCode;
	
	/* 年休付与日数 */
	private GrantDays grantDays;
	
	/* 時間年休上限日数 */
	private LimitedDays limitedDays;
	
	/* 半日年休上限回数 */
	private LimitedTimes limitedTimes;
	
	/* 勤続年数月数 */
	private NumberMonths numberMonths;
	
	/* 勤続年数年数 */
	private NumberYears numberYears;
	
	/* 付与基準日 */
	private int grantDayType;
	
	/* 一斉付与する */
	private int grantSimultaneity;

	public YearHolidayGrant(String companyId, int yearHolidayGrantNo, int conditionNo, YearHolidayCode yearHolidayCode,
			GrantDays grantDays, LimitedDays limitedDays, LimitedTimes limitedTimes, NumberMonths numberMonths, NumberYears numberYears, int grantDayType,
			int grantSimultaneity) {
		
		this.companyId = companyId;
		this.yearHolidayGrantNo = yearHolidayGrantNo;
		this.conditionNo = conditionNo;
		this.yearHolidayCode = yearHolidayCode;
		this.grantDays = grantDays;
		this.limitedDays = limitedDays;
		this.limitedTimes = limitedTimes;
		this.numberMonths = numberMonths;
		this.numberYears = numberYears;
		this.grantDayType = grantDayType;
		this.grantSimultaneity = grantSimultaneity;
	}
}
