package nts.uk.ctx.at.shared.dom.vacation.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class UseDateDeadlineFromDatePeriodImpl implements UseDateDeadlineFromDatePeriod{
	@Inject
	private ClosureService closureService;
	@Override
	public GeneralDate useDateDeadline(String employmentCd, ExpirationTime expirationDate, GeneralDate baseDate) {
		//締めを取得する
		Closure closureData = closureService.getClosurByEmployment(employmentCd);
		if(closureData == null) {
			return null;
		}
		//休暇使用期限年月を算出する
		YearMonth ym = this.yearMonthUseDeadline(baseDate, expirationDate);
		//休暇使用期限基準日を作成する
		GeneralDate ymd = this.getUseDeadlineDate(ym, baseDate.day());
		//締め->アルゴリズム「指定した年月日時点の締め期間を取得する」を実行する
		Optional<ClosurePeriod> periodByYmd = closureData.getClosurePeriodByYmd(ymd);
		if(periodByYmd.isPresent()) {
			return periodByYmd.get().getPeriod().end();
		}
		
		return null;
	}
	/**
	 * 休暇使用期限年月を算出する
	 * @param baseDate
	 * @param expirationDate
	 * @return
	 */
	private YearMonth yearMonthUseDeadline(GeneralDate baseDate, ExpirationTime expirationDate) {
		int year = baseDate.year();
		int month = baseDate.month();
		//休暇使用期限をチェックする
		if(expirationDate == ExpirationTime.ONE_MONTH
				|| expirationDate == ExpirationTime.TWO_MONTH
				|| expirationDate == ExpirationTime.THREE_MONTH
				|| expirationDate == ExpirationTime.FOUR_MONTH
				|| expirationDate == ExpirationTime.FIVE_MONTH
				|| expirationDate == ExpirationTime.SIX_MONTH
				|| expirationDate == ExpirationTime.SEVEN_MONTH
				|| expirationDate == ExpirationTime.EIGHT_MONTH
				|| expirationDate == ExpirationTime.NINE_MONTH
				|| expirationDate == ExpirationTime.TEN_MONTH
				|| expirationDate == ExpirationTime.ELEVEN_MONTH) {
			//休暇使用期限年月を使用期限までの月数分進める
			return YearMonth.of(year, month).addMonths(expirationDate.addValue);
		} else if (expirationDate == ExpirationTime.ONE_YEAR) {
			return YearMonth.of(year + 1, month);
		}
		return YearMonth.of(year, month);
	}
	/**
	 * 休暇使用期限基準日を作成する
	 * @param ym
	 * @param dates
	 * @return
	 */
	private GeneralDate getUseDeadlineDate(YearMonth ym, int dates) {
		//指定の年月と日の組み合わせが日付として成立するかをチェックする
		try {
			return GeneralDate.ymd(ym.year(), ym.month(), dates);
		} catch (Exception ex) {
			YearMonth nextMonth = ym.addMonths(1);
			GeneralDate nextMonthDate = GeneralDate.ymd(nextMonth.year(), nextMonth.month(), 1);
			return nextMonthDate.addDays(-1);
		}
	}

}
