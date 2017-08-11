package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import lombok.Value;

@Value
public class YearHolidayGrantDto {
	/* 会社ID */
	private String companyId;
	
	/* 年休付与NO */
	private int yearHolidayGrantNo;
	
	/* 条件NO */
	private int conditionNo;
	
	/* 年休付与テーブル設定コード */
	private String yearHolidayCode;
	
	/* 年休付与日数 */
	private int grantDays;
	
	/* 時間年休上限日数 */
	private int limitedDays;
	
	/* 半日年休上限回数 */
	private int limitedTimes;
	
	/* 勤続年数月数 */
	private int numberMonths;
	
	/* 勤続年数年数 */
	private int numberYears;
	
	/* 付与基準日 */
	private int grantDayType;
	
	/* 一斉付与する */
	private int grantSimultaneity;
	
	public static YearHolidayGrantDto fromDomain(YearHolidayGrantDto domain){
		return new YearHolidayGrantDto(domain.getCompanyId(),
					domain.getYearHolidayGrantNo(),
					domain.getConditionNo(),
					domain.getYearHolidayCode(),
					domain.getGrantDays(),
					domain.getLimitedDays(),
					domain.getLimitedTimes(),
					domain.getNumberMonths(),
					domain.getNumberYears(),
					domain.getGrantDayType(),
					domain.getGrantSimultaneity());
	}
}
