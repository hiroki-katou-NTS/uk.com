package nts.uk.ctx.exio.dom.qmm.setperiodcycle;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 有効期間とサイクルの設定
 */
@Getter
public class SetPeriodCycle extends AggregateRoot {

	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

	/**
	 * サイクル設定区分
	 */
	private CycleSettingAtr cycleSettingAtr;

	/**
	 * 対象月リスト
	 */
	private Optional<Integer> january;

	/**
	 * 対象月リスト
	 */
	private Optional<Integer> february;

	/**
	 * 対象月リスト
	 */
	private Optional<Integer> march;

	/**
	 * 対象月リスト
	 */
	private Optional<Integer> april;

	/**
	 * 対象月リスト
	 */
	private Optional<Integer> may;

	/**
	 * 対象月リスト
	 */
	private Optional<Integer> june;

	/**
	 * 対象月リスト
	 */
	private Optional<Integer> july;

	/**
	 * 対象月リスト
	 */
	private Optional<Integer> august;

	/**
	 * 対象月リスト
	 */
	private Optional<Integer> september;

	/**
	 * 対象月リスト
	 */
	private Optional<Integer> october;

	/**
	 * 対象月リスト
	 */
	private Optional<Integer> november;

	/**
	 * 対象月リスト
	 */
	private Optional<Integer> december;

	/**
	 * 有効期間設定区分
	 */
	private PeriodAtr periodAtr;

	/**
	 * 終了年
	 */
	private Optional<Integer> endYear;

	/**
	 * 開始年
	 */
	private Optional<Integer> startYear;

	public SetPeriodCycle(String salaryItemId, int cycleSettingAtr, int january, int february, int march, int april,
			int may, int june, int july, int august, int september, int october, int november, int december,
			int periodAtr, int endYear, int startYear) {
		super();
		this.salaryItemId = salaryItemId;
		this.cycleSettingAtr = EnumAdaptor.valueOf(cycleSettingAtr, CycleSettingAtr.class);
		this.january = Optional.ofNullable(january);
		this.february = Optional.ofNullable(february);
		this.march = Optional.ofNullable(march);
		this.april = Optional.ofNullable(april);
		this.may = Optional.ofNullable(may);
		this.june = Optional.ofNullable(june);
		this.july = Optional.ofNullable(july);
		this.august = Optional.ofNullable(august);
		this.september = Optional.ofNullable(september);
		this.october = Optional.ofNullable(october);
		this.november = Optional.ofNullable(november);
		this.december = Optional.ofNullable(december);
		this.periodAtr = EnumAdaptor.valueOf(periodAtr, PeriodAtr.class);
		this.endYear = Optional.ofNullable(endYear);
		this.startYear = Optional.ofNullable(startYear);
	}

}
