package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;

/**
 * 
 * B - Screen
 * @author TanLV
 *
 */
@Data
@AllArgsConstructor
public class GrantHolidayTblDto {
	/* 会社ID */
	private String companyId;
	
	/* 年休付与NO */
	private int grantYearHolidayNo;
	
	/* 条件NO */
	private int conditionNo;
	
	/* 年休付与テーブル設定コード */
	private String yearHolidayCode;
	
	/* 年休付与日数 */
	private BigDecimal grantDays;
	
	/* 時間年休上限日数 */
	private int limitedTimeHdDays;
	
	/* 半日年休上限回数 */
	private int limitedHalfHdCnt;
	
	/* 勤続年数月数 */
	private Integer lengthOfServiceMonths;
	
	/* 勤続年数年数 */
	private Integer lengthOfServiceYears;
	
	/* 付与基準日 */
	private int grantReferenceDate;
	
	/* 一斉付与する */
	private int grantSimultaneity;
	
	private GeneralDate grantDate;
	
	public static GrantHolidayTblDto fromDomain(GrantHdTbl domain){
		return new GrantHolidayTblDto(
				domain.getCompanyId(),
				domain.getGrantYearHolidayNo(),
				domain.getConditionNo(),
				domain.getYearHolidayCode().v(),
				domain.getGrantDays().v(),
				domain.getLimitedTimeHdDays().v(),
				domain.getLimitedHalfHdCnt().v(),
				domain.getLengthOfServiceMonths().v(),
				domain.getLengthOfServiceYears().v(),
				domain.getGrantReferenceDate().value,
				domain.getGrantSimultaneity().value,
				domain.getGrantDate()
		);
	}
}
