package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
/**
 * 勤続年数テーブル
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class LengthServiceTbl extends AggregateRoot{
	/* 会社ID */
	private String companyId;

	/* 年休付与テーブル設定コード */
	private YearHolidayCode yearHolidayCode;
	
	/* 付与回数 */
	private GrantNum grantNum;
	
	/* 一斉付与する */
	private GrantSimultaneity allowStatus;
	
	/* 付与基準日 */
	private GrantReferenceDate standGrantDay;

	/* 年数 */
	private LimitedTimeHdDays year;
	
	/* 月数 */
	private Month month;
	
	private GeneralDate grantDate;
	
	/**
	 * validate length service table
	 * @param grantHolidayList
	 */
	public static void validateInput(List<LengthServiceTbl> grantHolidayList) {
		// 重複した勤続年数の登録不可	
		List<YearMonthHoliday> yearMonthHoliday = new ArrayList<>();

		for (int i = 0; i < grantHolidayList.size(); i++) {
			LengthServiceTbl currentCondition = grantHolidayList.get(i);
			
			// 年数が入力されており、月数が未入力の場合「X年0ヶ月」として登録する
			if(currentCondition.getYear() != null && currentCondition.getMonth() == null){
				currentCondition.setMonth(new Month(0));
			}
			
			// 月数が入力されており、年数が未入力の場合「0年Xヶ月」として登録する
			if(currentCondition.getYear() == null && currentCondition.getMonth() != null){
				currentCondition.setYear(new LimitedTimeHdDays(0));
			}
			
			if (currentCondition.getMonth() == null && currentCondition.getYear() == null) {
				throw new BusinessException("Msg_270");
			}
			
			// 勤続年数、0年0ヶ月は登録不可
			if (currentCondition.getMonth().v() == 0 && currentCondition.getYear().v() == 0) {
				throw new BusinessException("Msg_268");
			}
			
			// 重複した勤続年数の登録不可
			YearMonthHoliday currentYearMonth = new YearMonthHoliday();
			currentYearMonth.setMonth(currentCondition.getMonth().v());
			currentYearMonth.setYear(currentCondition.getYear().v());
			
			if (yearMonthHoliday.stream().anyMatch(x -> x.equals(currentYearMonth))) {
				throw new BusinessException("Msg_266");
			}
			
			yearMonthHoliday.add(currentYearMonth);
						
			// 年数、月数ともに未入力の場合登録不可
			if (currentCondition.getYear() == null && currentCondition.getMonth() == null) {
				throw new BusinessException("Can't register 年数、月数ともに未入力の場合登録不可");
			}
						
			if (i == 0) {
				continue;
			}

			// 勤続年数は上から昇順になっていること
			int firstValueYear = grantHolidayList.get(i - 1).getYear().v();
			int secondValueYear = currentCondition.getYear().v();
			if (firstValueYear > secondValueYear) {
				throw new BusinessException("Msg_269");
			}			
		}

	}

	public static LengthServiceTbl createFromJavaType(String companyId, String yearHolidayCode, int grantNum, int allowStatus, 
														int standGrantDay, Integer year, Integer month){
		return new LengthServiceTbl(companyId, new YearHolidayCode(yearHolidayCode), 
									new GrantNum(grantNum), EnumAdaptor.valueOf(allowStatus, GrantSimultaneity.class), 
									EnumAdaptor.valueOf(standGrantDay, GrantReferenceDate.class),
									year == null ? null : new LimitedTimeHdDays(year), month == null ? null : new Month(month));
	}
	
	/**
	 * 
	 * @param grantHolidayList
	 */
	public void calculateGrantDate(GeneralDate referenceDate, GeneralDate simultaneousGrantDate,
			UseSimultaneousGrant useSimultaneousGrant) {
		GeneralDate c = GeneralDate.ymd(referenceDate.year(), referenceDate.month(), referenceDate.day());
		c = c.addMonths(month.v());
		c = c.addYears(year.v());
		
		if (UseSimultaneousGrant.USE.equals(useSimultaneousGrant)) {
			if (GrantSimultaneity.USE.equals(this.allowStatus)) {
				// 処理名4.付与日シュミレーション計算処理について
				if ((c.month() == simultaneousGrantDate.month() && c.day() >= simultaneousGrantDate.day()) || c.month() > simultaneousGrantDate.month()) {
					this.grantDate = GeneralDate.ymd(c.year(), simultaneousGrantDate.month(), simultaneousGrantDate.day());
				} else {
					this.grantDate = GeneralDate.ymd(c.year() -1 , simultaneousGrantDate.month(), simultaneousGrantDate.day());
				}
			} else {
				this.grantDate = c;
			}

		} else {
			// 処理名3.付与日シュミレーション計算処理について
			this.grantDate = c;
		}
	}

	public LengthServiceTbl(String companyId, YearHolidayCode yearHolidayCode, GrantNum grantNum,
			GrantSimultaneity allowStatus, GrantReferenceDate standGrantDay, LimitedTimeHdDays year, Month month) {
		
		this.companyId = companyId;
		this.yearHolidayCode = yearHolidayCode;
		this.grantNum = grantNum;
		this.allowStatus = allowStatus;
		this.standGrantDay = standGrantDay;
		this.year = year;
		this.month = month;
	}

}
