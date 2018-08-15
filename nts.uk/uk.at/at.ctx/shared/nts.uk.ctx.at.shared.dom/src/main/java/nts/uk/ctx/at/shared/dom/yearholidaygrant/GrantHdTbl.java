package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 年休付与テーブル
 * 
 * @author TanLV
 *
 */

@Data
@AllArgsConstructor
public class GrantHdTbl extends AggregateRoot {
	/* 会社ID */
	private String companyId;

	/* 条件NO */
	private int conditionNo;
	
	/* 年休付与テーブル設定コード */
	private YearHolidayCode yearHolidayCode;
	
	/* 付与回数 */
	private GrantNum grantNum;

	/* 年休付与日数 */
	private GrantDays grantDays;

	/* 時間年休上限日数 */
	private Optional<LimitedTimeHdDays> limitTimeHd;

	/* 半日年休上限回数 */
	private Optional<LimitedHalfHdCnt> limitDayYear;
	
	@Override
	public void validate() {
		super.validate();
		// 一斉付与する ＝ TRUE の場合 付与基準日は年休付与基準日でなければならない

		// 利用区分がTRUEの付与条件は、選択されている計算方法の条件値が入力されていること
	}


	
	/**
	 * 
	 * @param companyId
	 * @param grantYearHolidayNo
	 * @param conditionNo
	 * @param yearHolidayCode
	 * @param grantDays
	 * @param limitedTimeHdDays
	 * @param limitedHalfHdCnt
	 * @param lengthOfServiceMonths
	 * @param lengthOfServiceYears
	 * @param grantReferenceDate
	 * @param grantSimultaneity
	 * @return
	 */
	public static GrantHdTbl createFromJavaType(String companyId, int conditionNo,
												String yearHolidayCode, int grantNum, 
												Double grantDays, Integer limitTimeHd, Integer limitDayYear) {
		return new GrantHdTbl(companyId, conditionNo, new YearHolidayCode(yearHolidayCode), 
				new GrantNum(grantNum), new GrantDays(grantDays), 
				Optional.ofNullable(limitTimeHd == null ? null : new LimitedTimeHdDays(limitTimeHd)), 
				Optional.ofNullable(limitDayYear == null ? null : new LimitedHalfHdCnt(limitDayYear)));
	}
}
