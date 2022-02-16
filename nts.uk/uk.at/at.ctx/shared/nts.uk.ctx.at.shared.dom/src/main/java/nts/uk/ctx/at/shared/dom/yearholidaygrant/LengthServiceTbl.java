package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
/**
 * 勤続年数テーブル
 * @author yennth
 *
 */
@Setter
@Getter
@AllArgsConstructor
public class LengthServiceTbl extends AggregateRoot implements Serializable{
	
	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/* 会社ID */
	private String companyId;

	/* 年休付与テーブル設定コード */
	private YearHolidayCode yearHolidayCode;

//	勤続年数
	private List<LengthOfService> lengthOfServices;

//	private GeneralDate grantDate;

	/**
	 * validate length service table
	 *
	 * @param grantHolidayList
	 */
	public static void validateInput(List<LengthOfService> lengthOfServices) {
		// 重複した勤続年数の登録不可
		List<YearMonthHoliday> yearMonthHoliday = new ArrayList<>();
		for (int i = 0; i < lengthOfServices.size(); i++) {
			LengthOfService currentCondition = lengthOfServices.get(i);

			// 年数が入力されており、月数が未入力の場合「X年0ヶ月」として登録する
			if (currentCondition.getYear() != null && currentCondition.getMonth() == null) {
				currentCondition.setMonth(new Month(0));
			}

			// 月数が入力されており、年数が未入力の場合「0年Xヶ月」として登録する
			if (currentCondition.getYear() == null && currentCondition.getMonth() != null) {
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
			int firstValueYear = lengthOfServices.get(i - 1).getYear().v();
			int secondValueYear = currentCondition.getYear().v();
			if (firstValueYear > secondValueYear) {
				throw new BusinessException("Msg_269");
			}
		}

	}


	public LengthServiceTbl(String companyId, YearHolidayCode yearHolidayCode, GrantNum grantNum,
			List<LengthOfService> lengthOfServices) {

		this.companyId = companyId;
		this.yearHolidayCode = yearHolidayCode;
		this.lengthOfServices = lengthOfServices;
	}

	/**
	 *
	 *
	 */
	public LengthOfService getLengthServiceByGrantNumber(GrantNum number) {
		return this.getLengthOfServices().stream().filter(c -> c.getGrantNum().equals(number)).findFirst()
				.orElse(this.getLengthOfServices().get(this.getLengthOfServices().size() - 1));
	}

	public int getLengthOfServicesSize() {
		return this.lengthOfServices.size();
	}

	public LengthOfService getALengthOfService(int num) {
		return this.lengthOfServices.get(num);
	}

}
