/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.pensionrate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.Ins2Rate;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.RoundingItem;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceGender;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.FundRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionChargeRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionPremiumRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRounding;

/**
 * The Class JpaPensionRateRepository.
 */
@Stateless
public class JpaPensionRateRepository extends JpaRepository implements PensionRateRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository
	 * #add(nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate)
	 */
	@Override
	public void add(PensionRate rate) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository
	 * #update(nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate)
	 */
	@Override
	public void update(PensionRate rate) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository
	 * #remove(java.lang.String, java.lang.Long)
	 */
	@Override
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository
	 * #findAll(int)
	 */
	@Override
	public List<PensionRate> findAll(int companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository
	 * #findById(java.lang.String)
	 */
	@Override
	public Optional<PensionRate> findById(String id) {
		
		List<FundRateItem> fundRateItems = new ArrayList<FundRateItem>(); 
		List<PensionPremiumRateItem> premiumRateItems = new ArrayList<PensionPremiumRateItem>();
		List<PensionRateRounding> roundingMethods = new ArrayList<PensionRateRounding>();
		
		PensionChargeRateItem pensionChargeRateItem = new PensionChargeRateItem();
		pensionChargeRateItem.setPersonalRate(new Ins2Rate(new BigDecimal(40.900)));
		pensionChargeRateItem.setCompanyRate(new Ins2Rate(new BigDecimal(40.900)));
		fundRateItems.add(new FundRateItem(PaymentType.Bonus, InsuranceGender.Female,pensionChargeRateItem,pensionChargeRateItem));
		fundRateItems.add(new FundRateItem(PaymentType.Bonus, InsuranceGender.Female,pensionChargeRateItem,pensionChargeRateItem));
		fundRateItems.add(new FundRateItem(PaymentType.Bonus, InsuranceGender.Female,pensionChargeRateItem,pensionChargeRateItem));
		fundRateItems.add(new FundRateItem(PaymentType.Bonus, InsuranceGender.Female,pensionChargeRateItem,pensionChargeRateItem));
		fundRateItems.add(new FundRateItem(PaymentType.Bonus, InsuranceGender.Female,pensionChargeRateItem,pensionChargeRateItem));
		fundRateItems.add(new FundRateItem(PaymentType.Bonus, InsuranceGender.Female,pensionChargeRateItem,pensionChargeRateItem));
		
		PensionPremiumRateItem premiumRateItem = new PensionPremiumRateItem();
		PensionChargeRateItem raiseChargeRate = new PensionChargeRateItem();
		raiseChargeRate.setPersonalRate(new Ins2Rate(new BigDecimal(40.900)));
		raiseChargeRate.setCompanyRate(new Ins2Rate(new BigDecimal(40.900)));
		premiumRateItem.setGenderType(InsuranceGender.Female);
		premiumRateItem.setPayType(PaymentType.Bonus);
		premiumRateItem.setRaiseChargeRate(raiseChargeRate);
		premiumRateItems.add(premiumRateItem);
		
		PensionRateRounding pensionRateRounding = new PensionRateRounding();
		RoundingItem ri = new RoundingItem();
		ri.setPersonalRoundAtr(RoundingMethod.Down4_Up5);
		ri.setPersonalRoundAtr(RoundingMethod.RoundDown);
		pensionRateRounding.setPayType(PaymentType.Bonus);
		pensionRateRounding.setRoundAtrs(ri);
		roundingMethods.add(pensionRateRounding);
		roundingMethods.add(pensionRateRounding);
		roundingMethods.add(pensionRateRounding);
		roundingMethods.add(pensionRateRounding);
		
		MonthRange mr = MonthRange.range(new YearMonth(55), new YearMonth(33));

		PensionRate mock = new PensionRate("String historyId", new CompanyCode("000000"), new OfficeCode("officeCode"), mr,
				new CommonAmount(new BigDecimal(9)),fundRateItems,premiumRateItems,
				new Ins2Rate(new BigDecimal(10)),roundingMethods);
		return Optional.of(mock);
	}

}
