package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.GetTightSetting.GetTightSettingResult;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;

/**
 * @author ThanhNX
 *
 *         使用期限を設定
 */
public class SettingExpirationDate {

	public static GeneralDate settingExp(ExpirationTime expirationTime,
			Optional<GetTightSettingResult> tightSettingResult, GeneralDate dateOccuDigest) {

		if (expirationTime == ExpirationTime.UNLIMITED) {
			// 9999/12/31を設定
			return GeneralDate.max();
		}

		if (expirationTime == ExpirationTime.THIS_MONTH) {
			// 逐次発生の休暇明細.年月日が含まれる締め終了日を設定
			int day = tightSettingResult.get().getClosureDate().getClosureDay().v();
			GeneralDate monthNext = dateOccuDigest.addMonths(1);
			return dateOccuDigest.day() > day ? setDayToDate(monthNext, day) : setDayToDate(dateOccuDigest, day);
		}
		if (expirationTime == ExpirationTime.END_OF_YEAR) {
			// 次の期首月の前日を設定
			int month = tightSettingResult.get().getStartMonth(), year;
			if (dateOccuDigest.month() > month) {
				year = dateOccuDigest.year() + 1;
			} else {
				year = dateOccuDigest.year();
			}
			GeneralDate ymd = GeneralDate.ymd(year, month, 1);
			return setDayToDate(ymd.addMonths(-1), ymd.addMonths(-1).lastDateInMonth()).addDays(-1);
		}

		// 逐次発生の休暇明細.年月日に休暇使用期限を加算する
		if (expirationTime.value >= ExpirationTime.ONE_MONTH.value
				&& expirationTime.value <= ExpirationTime.ONE_YEAR.value) {
			dateOccuDigest.addDays(expirationTime.value);
		} else if (expirationTime.value >= ExpirationTime.END_OF_YEAR.value) {
			return dateOccuDigest.addDays(12 - dateOccuDigest.month());
		}

		return GeneralDate.max();

	}

	private static GeneralDate setDayToDate(GeneralDate date, int day) {
		return GeneralDate.ymd(date.year(), date.month(), day > date.lastDateInMonth() ? date.lastDateInMonth() : day);

	}
}
