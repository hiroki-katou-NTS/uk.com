package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import lombok.Getter;

/**
 * 年休付与テーブル
 * 
 * @author TanLV
 *
 */

@Getter
public class GrantHdTbl {
	/* 会社ID */
	private String companyId;
	
	/* 年休付与NO */
	private int grantYearHolidayNo;
	
	/* 条件NO */
	private int conditionNo;
	
	/* 年休付与テーブル設定コード */
	private YearHolidayCode yearHolidayCode;
	
	/* 年休付与日数 */
	private GrantDays grantDays;
	
	/* 時間年休上限日数 */
	private LimitedTimeHdDays limitedTimeHdDays;
	
	/* 半日年休上限回数 */
	private LimitedHalfHdCnt limitedHalfHdCnt;
	
	/* 勤続年数月数 */
	private LengthOfServiceMonths lengthOfServiceMonths;
	
	/* 勤続年数年数 */
	private LengthOfServiceYears lengthOfServiceYears;
	
	/* 付与基準日 */
	private int grantReferenceDate;
	
	/* 一斉付与する */
	private int grantSimultaneity;

	public GrantHdTbl(String companyId, int grantYearHolidayNo, int conditionNo, YearHolidayCode yearHolidayCode,
			GrantDays grantDays, LimitedTimeHdDays limitedTimeHdDays, LimitedHalfHdCnt limitedHalfHdCnt, LengthOfServiceMonths lengthOfServiceMonths, 
			LengthOfServiceYears lengthOfServiceYears, int grantReferenceDate, int grantSimultaneity) {
		
		this.companyId = companyId;
		this.grantYearHolidayNo = grantYearHolidayNo;
		this.conditionNo = conditionNo;
		this.yearHolidayCode = yearHolidayCode;
		this.grantDays = grantDays;
		this.limitedTimeHdDays = limitedTimeHdDays;
		this.limitedHalfHdCnt = limitedHalfHdCnt;
		this.lengthOfServiceMonths = lengthOfServiceMonths;
		this.lengthOfServiceYears = lengthOfServiceYears;
		this.grantReferenceDate = grantReferenceDate;
		this.grantSimultaneity = grantSimultaneity;
	}
}
