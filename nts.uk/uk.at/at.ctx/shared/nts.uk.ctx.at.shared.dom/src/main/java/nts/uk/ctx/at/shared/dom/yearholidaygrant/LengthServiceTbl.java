package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceset.primitives.Month;
/**
 * 勤続年数テーブル
 * @author yennth
 *
 */
@Value
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
	
	
	/**
	 * validate length service table
	 * @param grantHolidayList
	 */
	public static void validateInput(List<LengthServiceTbl> grantHolidayList) {
		// 重複した勤続年数の登録不可	
		List<YearMonthHoliday> yearMonths = new ArrayList<>();
		
		for (int i = 0; i < grantHolidayList.size(); i++) {
			LengthServiceTbl currentCondition = grantHolidayList.get(i);
			
			if (currentCondition.getMonth() == null && currentCondition.getYear() == null) {
				throw new BusinessException("Msg_270");
			}
			
			// 勤続年数、0年0ヶ月は登録不可
			if (currentCondition.getMonth().v() == 0 && currentCondition.getYear().v() == 0) {
				throw new BusinessException("Msg_268");
			}
			
			// 重複した勤続年数の登録不可
			YearMonthHoliday currentYearMonthHd = new YearMonthHoliday(currentCondition.getYear().v(), currentCondition.getMonth().v());
			if (yearMonths.stream().anyMatch(x -> x.equals(currentYearMonthHd))) {
				throw new BusinessException("Msg_266");
			}
			yearMonths.add(new YearMonthHoliday(currentCondition.getYear().v(), currentCondition.getMonth().v()));
						
//			// 勤続年数が入力されている場合、付与日数を入力すること
//			if (currentCondition.getGrantReferenceDate() == null || currentCondition.getGrantReferenceDate().value == null) {
//				throw new BusinessException("Msg_270");
//			}
						
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

	public static LengthServiceTbl createFromJavaType(String companyId, String yearHolidayCode, int grantNum, int grantSimultaneity, 
														int grantReferenceDate, int day, int month){
		return new LengthServiceTbl(companyId, new YearHolidayCode(yearHolidayCode), 
									new GrantNum(grantNum), EnumAdaptor.valueOf(grantSimultaneity, GrantSimultaneity.class), 
									EnumAdaptor.valueOf(grantReferenceDate, GrantReferenceDate.class),
									new LimitedTimeHdDays(day), new Month(month));
	}
	
}
