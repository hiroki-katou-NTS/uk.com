/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthrate.command;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.find.AddNewHistoryDto;
import nts.uk.ctx.pr.core.dom.insurance.Ins3Rate;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.RoundingItem;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthChargeRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRounding;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceType;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceRateItem;

/**
 * The Class RegisterHealthInsuranceCommand.
 */
@Getter
@Setter
public class RegisterHealthInsuranceCommand extends AddNewHistoryDto {
	RegisterHealthInsuranceCommand command = this;
	//init default rate values
	public Set<InsuranceRateItem> setDafaultRateItems() {
		Set<InsuranceRateItem> setItem = new HashSet<InsuranceRateItem>();
		InsuranceRateItem item1 = new InsuranceRateItem(PaymentType.Salary, HealthInsuranceType.Basic,
				new HealthChargeRateItem(new Ins3Rate(new BigDecimal(0)), new Ins3Rate(new BigDecimal(0))));
		setItem.add(item1);
		InsuranceRateItem item2 = new InsuranceRateItem(PaymentType.Salary, HealthInsuranceType.General,
				new HealthChargeRateItem(new Ins3Rate(new BigDecimal(0)), new Ins3Rate(new BigDecimal(0))));
		setItem.add(item2);
		InsuranceRateItem item3 = new InsuranceRateItem(PaymentType.Salary, HealthInsuranceType.Nursing,
				new HealthChargeRateItem(new Ins3Rate(new BigDecimal(0)), new Ins3Rate(new BigDecimal(0))));
		setItem.add(item3);
		InsuranceRateItem item4 = new InsuranceRateItem(PaymentType.Salary, HealthInsuranceType.Special,
				new HealthChargeRateItem(new Ins3Rate(new BigDecimal(0)), new Ins3Rate(new BigDecimal(0))));
		setItem.add(item4);
		InsuranceRateItem item5 = new InsuranceRateItem(PaymentType.Bonus, HealthInsuranceType.Basic,
				new HealthChargeRateItem(new Ins3Rate(new BigDecimal(0)), new Ins3Rate(new BigDecimal(0))));
		setItem.add(item5);
		InsuranceRateItem item6 = new InsuranceRateItem(PaymentType.Bonus, HealthInsuranceType.General,
				new HealthChargeRateItem(new Ins3Rate(new BigDecimal(0)), new Ins3Rate(new BigDecimal(0))));
		setItem.add(item6);
		InsuranceRateItem item7 = new InsuranceRateItem(PaymentType.Bonus, HealthInsuranceType.Nursing,
				new HealthChargeRateItem(new Ins3Rate(new BigDecimal(0)), new Ins3Rate(new BigDecimal(0))));
		setItem.add(item7);
		InsuranceRateItem item8 = new InsuranceRateItem(PaymentType.Bonus, HealthInsuranceType.Special,
				new HealthChargeRateItem(new Ins3Rate(new BigDecimal(0)), new Ins3Rate(new BigDecimal(0))));
		setItem.add(item8);
		return setItem;
	}
	
	//init default rounding values
		public Set<HealthInsuranceRounding> setDafaultRounding() {
			Set<HealthInsuranceRounding> setItem = new HashSet<HealthInsuranceRounding>();
			RoundingItem salRounding = new RoundingItem();
			salRounding.setCompanyRoundAtr(RoundingMethod.RoundUp);
			salRounding.setPersonalRoundAtr(RoundingMethod.RoundUp);
			HealthInsuranceRounding item1 = new HealthInsuranceRounding(PaymentType.Salary,salRounding);
			setItem.add(item1);
			RoundingItem bnsRounding = new RoundingItem();
			bnsRounding.setCompanyRoundAtr(RoundingMethod.RoundUp);
			bnsRounding.setPersonalRoundAtr(RoundingMethod.RoundUp);
			HealthInsuranceRounding item2 = new HealthInsuranceRounding(PaymentType.Bonus,bnsRounding);
			setItem.add(item2);
			
			return setItem;
		}
}
