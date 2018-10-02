package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 
 * @author thanh.tq 固定的賃金の設定
 *
 */
@Getter
public class FixedWage extends DomainObject {

	/**
	 * 固定的賃金の設定区分
	 */
	private SettingClassification settingAtr;

	/**
	 * 全員一律の設定
	 */
	private Optional<CategoryFixedWage> everyoneEqualSet;

	/**
	 * 給与契約形態ごとの設定
	 */
	private PerSalaryContractType perSalaryContractType;

	public FixedWage(int settingAtr, Integer everyoneEqualSet, Integer monthlySalary, Integer hourlyPay, Integer dayPayee,
			Integer monthlySalaryPerday) {
		super();
		this.settingAtr = EnumAdaptor.valueOf(settingAtr, SettingClassification.class);
		this.everyoneEqualSet = everyoneEqualSet == null? Optional.empty() : Optional.of(EnumAdaptor.valueOf(everyoneEqualSet, CategoryFixedWage.class));
		this.perSalaryContractType = new PerSalaryContractType(monthlySalary, hourlyPay, dayPayee, monthlySalaryPerday);
	}
}
