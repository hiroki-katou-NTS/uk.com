package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 年休付与テーブル
 * 
 * @author TanLV
 *
 */

@Value
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
												BigDecimal grantDays, Integer limitTimeHd, Integer limitDayYear) {
		return new GrantHdTbl(companyId, conditionNo, new YearHolidayCode(yearHolidayCode), 
				new GrantNum(grantNum), new GrantDays(grantDays), 
				Optional.ofNullable(limitTimeHd == null ? null : new LimitedTimeHdDays(limitTimeHd)), 
				Optional.ofNullable(limitDayYear == null ? null : new LimitedHalfHdCnt(limitDayYear)));
	}

	/**
	 * 
	 * @param grantHolidayList
	 */
	public void calculateGrantDate(GeneralDate referenceDate, GeneralDate simultaneousGrantDate,
			UseSimultaneousGrant useSimultaneousGrant) {
//		GeneralDate c = GeneralDate.ymd(referenceDate.year(), referenceDate.month(), referenceDate.day());
//		c = c.addMonths(lengthOfServiceMonths.v());
//		c = c.addYears(lengthOfServiceYears.v());
//		
//		if (UseSimultaneousGrant.USE.equals(useSimultaneousGrant)) {
//			if (GrantSimultaneity.USE.equals(this.grantSimultaneity)) {
//				// 処理名4.付与日シュミレーション計算処理について
//				if ((c.month() == simultaneousGrantDate.month() && c.day() >= simultaneousGrantDate.day()) || c.month() > simultaneousGrantDate.month()) {
//					this.grantDate = GeneralDate.ymd(c.year(), simultaneousGrantDate.month(), simultaneousGrantDate.day());
//				} else {
//					this.grantDate = GeneralDate.ymd(c.year() -1 , simultaneousGrantDate.month(), simultaneousGrantDate.day());
//				}
//			} else {
//				this.grantDate = c;
//			}
//
//		} else {
//			// 処理名3.付与日シュミレーション計算処理について
//			this.grantDate = c;
//		}
	}

	public static void validateInput(List<GrantHdTbl> grantHolidayList) {
		// 重複した勤続年数の登録不可	
		List<YearMonthHoliday> yearMonths = new ArrayList<>();
		
//		for (int i = 0; i < grantHolidayList.size(); i++) {
//			GrantHdTbl currentCondition = grantHolidayList.get(i);
//			
//			if (currentCondition.getLengthOfServiceMonths() == null && currentCondition.getLengthOfServiceYears() == null) {
//				throw new BusinessException("Msg_270");
//			}
//			
//			// 勤続年数、0年0ヶ月は登録不可
//			if (currentCondition.getLengthOfServiceMonths().v() == 0 && currentCondition.getLengthOfServiceYears().v() == 0) {
//				throw new BusinessException("Msg_268");
//			}
//			
//			// 重複した勤続年数の登録不可
//			YearMonthHoliday currentYearMonthHd = new YearMonthHoliday(currentCondition.getLengthOfServiceYears().v(), currentCondition.getLengthOfServiceMonths().v());
//			if (yearMonths.stream().anyMatch(x -> x.equals(currentYearMonthHd))) {
//				throw new BusinessException("Msg_266");
//			}
//			yearMonths.add(new YearMonthHoliday(currentCondition.getLengthOfServiceYears().v(), currentCondition.getLengthOfServiceMonths().v()));
//						
//			// 勤続年数が入力されている場合、付与日数を入力すること
//			if (currentCondition.getGrantDays() == null || currentCondition.getGrantDays().v() == null) {
//				throw new BusinessException("Msg_270");
//			}
//						
//			if (i == 0) {
//				continue;
//			}
//
//			// 勤続年数は上から昇順になっていること
//			int firstValueYear = grantHolidayList.get(i - 1).getLengthOfServiceYears().v();
//			int secondValueYear = currentCondition.getLengthOfServiceYears().v();
//			if (firstValueYear > secondValueYear) {
//				throw new BusinessException("Msg_269");
//			}			
//		}

	}


}
