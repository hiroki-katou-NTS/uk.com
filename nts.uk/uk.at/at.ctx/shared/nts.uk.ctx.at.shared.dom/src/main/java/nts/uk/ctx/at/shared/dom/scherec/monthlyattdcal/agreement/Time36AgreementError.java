package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * refactor 5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).月の勤怠計算.36協定実績.36協定エラー
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class Time36AgreementError {
	
	/**
	 * しきい値
	 */
	private int threshold;
	
	/**
	 * 実績時間
	 */
	private int agreementTime;
	
	/**
	 * 種類
	 */
	private Time36AgreementErrorAtr time36AgreementErrorAtr;
	
	/**
	 * 複数月エラー期間
	 */
	private Optional<YearMonthPeriod> opYearMonthPeriod;
}
