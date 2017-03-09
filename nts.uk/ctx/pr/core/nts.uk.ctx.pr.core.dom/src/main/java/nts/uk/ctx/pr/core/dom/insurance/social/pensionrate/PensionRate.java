/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionrate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.base.simplehistory.History;
import nts.uk.ctx.pr.core.dom.insurance.CalculateMethod;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.Ins2Rate;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;

/**
 * The Class PensionRate.
 */
@Getter
public class PensionRate extends AggregateRoot implements History<PensionRate> {

	/** The history id. */
	// historyId
	private String historyId;

	/** The company code. */
	private CompanyCode companyCode;

	/** The office code. */
	private OfficeCode officeCode;

	/** The apply range. */
	@Setter
	private MonthRange applyRange;

	/** The fund input apply. */
	@Setter
	private Boolean fundInputApply;

	/** The auto calculate. */
	@Setter
	private CalculateMethod autoCalculate;

	/** The premium rate items. */
	@Setter
	private Set<PensionPremiumRateItem> premiumRateItems;

	/** The fund rate items. */
	@Setter
	private Set<FundRateItem> fundRateItems;

	/** The rounding methods. */
	@Setter
	private Set<PensionRateRounding> roundingMethods;

	/** The max amount. */
	@Setter
	private CommonAmount maxAmount;

	/** The child contribution rate. */
	@Setter
	private Ins2Rate childContributionRate;

	/**
	 * Instantiates a new pension rate.
	 */
	private PensionRate() {
		this.historyId = IdentifierUtil.randomUniqueId();
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new pension rate.
	 *
	 * @param memento the memento
	 */
	public PensionRate(PensionRateGetMemento memento) {
		this.historyId = memento.getHistoryId();
		this.companyCode = memento.getCompanyCode();
		this.officeCode = memento.getOfficeCode();
		this.applyRange = memento.getApplyRange();
		this.fundInputApply = memento.getFundInputApply();
		this.autoCalculate = memento.getAutoCalculate();
		this.maxAmount = memento.getMaxAmount();
		this.fundRateItems = memento.getFundRateItems();
		this.premiumRateItems = memento.getPremiumRateItems();
		this.childContributionRate = memento.getChildContributionRate();
		this.roundingMethods = memento.getRoundingMethods();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PensionRateSetMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setCompanyCode(this.companyCode);
		memento.setOfficeCode(this.officeCode);
		memento.setApplyRange(this.applyRange);
		memento.setFundInputApply(this.fundInputApply);
		memento.setAutoCalculate(this.autoCalculate);
		memento.setMaxAmount(this.maxAmount);
		memento.setFundRateItems(this.fundRateItems);
		memento.setPremiumRateItems(this.premiumRateItems);
		memento.setChildContributionRate(this.childContributionRate);
		memento.setRoundingMethods(this.roundingMethods);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#getUuid()
	 */
	@Override
	public String getUuid() {
		return this.historyId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#getMasterCode()
	 */
	@Override
	public PrimitiveValue<String> getMasterCode() {
		return this.getOfficeCode();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#getStart()
	 */
	@Override
	public YearMonth getStart() {
		return this.getApplyRange().getStartMonth();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#getEnd()
	 */
	@Override
	public YearMonth getEnd() {
		return this.getApplyRange().getEndMonth();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#setStart(nts.arc.time.YearMonth)
	 */
	@Override
	public void setStart(YearMonth yearMonth) {
		this.applyRange = MonthRange.range(yearMonth, this.applyRange.getEndMonth());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#setEnd(nts.arc.time.YearMonth)
	 */
	@Override
	public void setEnd(YearMonth yearMonth) {
		this.applyRange = MonthRange.range(this.applyRange.getStartMonth(), yearMonth);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#copyWithDate(nts.arc.time.YearMonth)
	 */
	@Override
	public PensionRate copyWithDate(YearMonth start) {
		PensionRate newPensionRate = new PensionRate();
		newPensionRate.companyCode = this.companyCode;
		newPensionRate.officeCode = this.officeCode;
		newPensionRate.applyRange = MonthRange.toMaxDate(start);
		newPensionRate.autoCalculate = this.autoCalculate;
		newPensionRate.fundInputApply = this.fundInputApply;
		newPensionRate.maxAmount = this.maxAmount;
		newPensionRate.childContributionRate = this.childContributionRate;
		newPensionRate.fundRateItems = this.fundRateItems;
		newPensionRate.premiumRateItems = this.premiumRateItems;
		newPensionRate.roundingMethods = this.roundingMethods;
		return newPensionRate;
	}

	/**
	 * Creates the with intial.
	 *
	 * @param companyCode the company code
	 * @param officeCode the office code
	 * @param startYearMonth the start year month
	 * @return the pension rate
	 */
	public static final PensionRate createWithIntial(CompanyCode companyCode, OfficeCode officeCode,
			YearMonth startYearMonth) {
		PensionRate pensionRate = new PensionRate();
		// TODO: review default values.
		pensionRate.companyCode = companyCode;
		pensionRate.officeCode = officeCode;
		pensionRate.applyRange = MonthRange.toMaxDate(startYearMonth);
		pensionRate.autoCalculate = CalculateMethod.Auto;
		pensionRate.fundInputApply = true;
		pensionRate.maxAmount = new CommonAmount(BigDecimal.ZERO);
		pensionRate.childContributionRate = new Ins2Rate(BigDecimal.ZERO);
		pensionRate.fundRateItems = new HashSet<FundRateItem>();
		pensionRate.premiumRateItems = new HashSet<PensionPremiumRateItem>();
		pensionRate.roundingMethods = new HashSet<PensionRateRounding>();
		return pensionRate;
	}

}
