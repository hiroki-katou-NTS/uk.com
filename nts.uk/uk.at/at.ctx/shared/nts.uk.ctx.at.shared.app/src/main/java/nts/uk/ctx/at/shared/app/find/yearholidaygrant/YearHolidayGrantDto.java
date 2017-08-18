package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import lombok.Value;

@Value
public class YearHolidayGrantDto {
	/* 会社ID */
	private String companyId;
	
	/* 年休付与NO */
	private int grantYearHolidayNo;
	
	/* 条件NO */
	private int conditionNo;
	
	/* 年休付与テーブル設定コード */
	private String yearHolidayCode;
	
	/* 年休付与日数 */
	private int grantDays;
	
	/* 時間年休上限日数 */
	private int limitedTimeHdDays;
	
	/* 半日年休上限回数 */
	private int limitedHalfHdCnt;
	
	/* 勤続年数月数 */
	private int lengthOfServiceMonths;
	
	/* 勤続年数年数 */
	private int lengthOfServiceYears;
	
	/* 付与基準日 */
	private int grantReferenceDate;
	
	/* 一斉付与する */
	private int grantSimultaneity;
	
	public static YearHolidayGrantDto fromDomain(YearHolidayGrantDto domain){
		return new YearHolidayGrantDto(domain.getCompanyId(),
					domain.getGrantYearHolidayNo(),
					domain.getConditionNo(),
					domain.getYearHolidayCode(),
					domain.getGrantDays(),
					domain.getLimitedTimeHdDays(),
					domain.getLimitedHalfHdCnt(),
					domain.getLengthOfServiceMonths(),
					domain.getLengthOfServiceYears(),
					domain.getGrantReferenceDate(),
					domain.getGrantSimultaneity());
	}
}
