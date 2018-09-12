package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 
 * @author thanh.tq 給与契約形態ごとの設定
 *
 */
@Getter
public class PerSalaryContractType extends DomainObject {
	/**
	 * 月給者
	 */
	private Optional<CategoryFixedWage> monthlySalary;

	/**
	 * 時給者
	 */
	private Optional<CategoryFixedWage> hourlyPay;

	/**
	 * 日給者
	 */
	private Optional<CategoryFixedWage> dayPayee;

	/**
	 * 日給月給者
	 */
	private Optional<CategoryFixedWage> monthlySalaryPerday;

	public PerSalaryContractType(Integer monthlySalary, Integer hourlyPay, Integer dayPayee,
			Integer monthlySalaryPerday) {
		super();
		this.monthlySalary = monthlySalary == null ? Optional.empty()
				: Optional.ofNullable(EnumAdaptor.valueOf(monthlySalary, CategoryFixedWage.class));
		this.hourlyPay = hourlyPay == null ? Optional.empty()
				: Optional.ofNullable(EnumAdaptor.valueOf(hourlyPay, CategoryFixedWage.class));
		this.dayPayee = dayPayee == null ? Optional.empty()
				: Optional.ofNullable(EnumAdaptor.valueOf(dayPayee, CategoryFixedWage.class));
		this.monthlySalaryPerday = monthlySalaryPerday == null ? Optional.empty()
				: Optional.ofNullable(EnumAdaptor.valueOf(monthlySalaryPerday, CategoryFixedWage.class));
	}

}
