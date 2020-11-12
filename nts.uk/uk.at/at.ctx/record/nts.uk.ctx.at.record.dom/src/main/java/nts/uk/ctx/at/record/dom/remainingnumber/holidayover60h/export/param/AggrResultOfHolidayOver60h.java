package nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;

/**
 * 60H超休の集計結果
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class AggrResultOfHolidayOver60h {

	/**
	 * 使用回数
	*/
	private UsedTimes usedTimes;
	/** 60H超休エラー情報 */
	private List<HolidayOver60hError> holidayOver60hErrors;
	/** 60H超休情報（期間終了日時点） */
	private HolidayOver60hInfo asOfPeriodEnd;
	/** 60H超休情報（期間終了日の翌日開始時点） */
	private HolidayOver60hInfo asOfStartNextDayOfPeriodEnd;
	/** 60H超休情報（消滅） */
	private Optional<HolidayOver60hInfo> lapsed;

	public void test() {

		/** 60H超休情報（消滅） */
		if (lapsed.isPresent()) {
			/** 60H超休情報（消滅）/ 残数/60H超休（マイナスあり）/ 残時間   */
			Integer x1 = lapsed.get().getRemainingNumber().getHolidayOver60hWithMinus().getRemainingTime().v();
		}

	}

	/**
	 * コンストラクタ
	 */
	public AggrResultOfHolidayOver60h(){
		this.usedTimes = new UsedTimes(0);
		this.holidayOver60hErrors = new ArrayList<HolidayOver60hError>();
		this.asOfPeriodEnd = new HolidayOver60hInfo();
		this.asOfStartNextDayOfPeriodEnd = new HolidayOver60hInfo();
		this.lapsed = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param asOfPeriodEnd 60H超休情報（期間終了日時点）
	 * @param asOfStartNextDayOfPeriodEnd 60H超休情報（期間終了日の翌日開始時点）
	 * @param lapsed 60H超休情報（消滅）
	 * @param holidayOver60hErrors 60H超休エラー情報
	 * @return 60H超休の集計結果
	 */
	public static AggrResultOfHolidayOver60h of(
			UsedTimes usedTimes,
			HolidayOver60hInfo asOfPeriodEnd,
			HolidayOver60hInfo asOfStartNextDayOfPeriodEnd,
			Optional<HolidayOver60hInfo> lapsed,
			List<HolidayOver60hError> holidayOver60hErrors){

		AggrResultOfHolidayOver60h domain = new AggrResultOfHolidayOver60h();
		domain.usedTimes = usedTimes;
		domain.holidayOver60hErrors = holidayOver60hErrors;
		domain.asOfPeriodEnd = asOfPeriodEnd;
		domain.asOfStartNextDayOfPeriodEnd = asOfStartNextDayOfPeriodEnd;
		domain.lapsed = lapsed;

		return domain;
	}

	/**
	 * 60H超休エラー情報の追加
	 * @param error 60H超休エラー情報
	 */
	public void addError(HolidayOver60hError error){

		if (this.holidayOver60hErrors.contains(error)) return;
		this.holidayOver60hErrors.add(error);
	}

}
