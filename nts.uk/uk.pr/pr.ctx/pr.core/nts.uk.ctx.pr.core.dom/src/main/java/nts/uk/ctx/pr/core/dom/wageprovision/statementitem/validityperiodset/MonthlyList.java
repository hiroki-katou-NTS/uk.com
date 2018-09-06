package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 
 * @author thanh.tq 対象月リスト
 *
 */
@Getter
public class MonthlyList extends DomainObject {
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

	public MonthlyList(int january, int february, int march, int april, int may, int june, int july, int august,
			int september, int october, int november, int december) {
		super();
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
	}

}
