package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.shr.com.context.AppContexts;

@Value
public class GrantHolidayTblCommand {
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
	
	/**
	 * Convert to domain object
	 * @return
	 */
	public GrantHdTbl toDomain() {
		String companyId = AppContexts.user().companyId();
		
		return  GrantHdTbl.createFromJavaType(companyId, grantYearHolidayNo, conditionNo, yearHolidayCode, grantDays,
				limitedTimeHdDays, limitedHalfHdCnt, lengthOfServiceMonths, lengthOfServiceYears, grantReferenceDate, grantSimultaneity);
	}
}
