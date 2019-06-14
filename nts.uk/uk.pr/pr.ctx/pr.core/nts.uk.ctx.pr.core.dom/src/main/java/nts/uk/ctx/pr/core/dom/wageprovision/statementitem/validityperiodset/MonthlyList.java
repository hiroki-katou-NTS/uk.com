package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
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
	private MonthlyTargetAtr january;

	/**
	 * 対象月リスト
	 */
	private MonthlyTargetAtr february;

	/**
	 * 対象月リスト
	 */
	private MonthlyTargetAtr march;

	/**
	 * 対象月リスト
	 */
	private MonthlyTargetAtr april;

	/**
	 * 対象月リスト
	 */
	private MonthlyTargetAtr may;

	/**
	 * 対象月リスト
	 */
	private MonthlyTargetAtr june;

	/**
	 * 対象月リスト
	 */
	private MonthlyTargetAtr july;

	/**
	 * 対象月リスト
	 */
	private MonthlyTargetAtr august;

	/**
	 * 対象月リスト
	 */
	private MonthlyTargetAtr september;

	/**
	 * 対象月リスト
	 */
	private MonthlyTargetAtr october;

	/**
	 * 対象月リスト
	 */
	private MonthlyTargetAtr november;

	/**
	 * 対象月リスト
	 */
	private MonthlyTargetAtr december;

	public MonthlyList(int january, int february, int march, int april, int may, int june, int july, int august,
			int september, int october, int november, int december) {
		super();
		this.january = EnumAdaptor.valueOf(january, MonthlyTargetAtr.class);
		this.february = EnumAdaptor.valueOf(february, MonthlyTargetAtr.class);
		this.march = EnumAdaptor.valueOf(march, MonthlyTargetAtr.class);
		this.april = EnumAdaptor.valueOf(april, MonthlyTargetAtr.class);
		this.may = EnumAdaptor.valueOf(may, MonthlyTargetAtr.class);
		this.june = EnumAdaptor.valueOf(june, MonthlyTargetAtr.class);
		this.july = EnumAdaptor.valueOf(july, MonthlyTargetAtr.class);
		this.august = EnumAdaptor.valueOf(august, MonthlyTargetAtr.class);
		this.september = EnumAdaptor.valueOf(september, MonthlyTargetAtr.class);
		this.october = EnumAdaptor.valueOf(october, MonthlyTargetAtr.class);
		this.november = EnumAdaptor.valueOf(november, MonthlyTargetAtr.class);
		this.december = EnumAdaptor.valueOf(december, MonthlyTargetAtr.class);
	}

}
