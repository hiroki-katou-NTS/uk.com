package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantSimultaneity;

/**
 * 
 * @author TanLV
 *
 */
@Value
public class GrantHolidayTbl {
	/* 年休付与NO */
	private int grantYearHolidayNo;
	
	/* 条件NO */
	private int conditionNo;
	
	/* 年休付与テーブル設定コード */
	private String yearHolidayCode;
	
	/* 年休付与日数 */
	private BigDecimal grantDays;
	
	/* 時間年休上限日数 */
	private Integer limitedTimeHdDays;
	
	/* 半日年休上限回数 */
	private Integer limitedHalfHdCnt;
	
	/* 勤続年数月数 */
	private Integer lengthOfServiceMonths;
	
	/* 勤続年数年数 */
	private Integer lengthOfServiceYears;
	
	/* 付与基準日 */
	private int grantReferenceDate;
	
	/* 一斉付与する */
	private boolean grantSimultaneity;
	
	/**
	 * Convert to domain object
	 * @return
	 */
	public GrantHdTbl toDomain(String companyId) {
		return  GrantHdTbl.createFromJavaType(companyId, grantYearHolidayNo, conditionNo, yearHolidayCode, grantDays,
				limitedTimeHdDays, limitedHalfHdCnt, lengthOfServiceMonths, lengthOfServiceYears, grantReferenceDate, grantSimultaneity ? GrantSimultaneity.USE.value : GrantSimultaneity.NOT_USE.value);
	}
}
