/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.avgearn.AvgEarnLevelMasterSetting;
import nts.uk.ctx.pr.core.dom.insurance.avgearn.AvgEarnLevelMasterSettingRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.FundRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionChargeRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.service.PensionRateService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RegisterPensionCommandHandler.
 */
@Stateless
public class RegisterPensionCommandHandler extends CommandHandler<RegisterPensionCommand> {

	/** The pension rate service. */
	@Inject
	PensionRateService pensionRateService;

	/** The pension rate repository. */
	@Inject
	PensionRateRepository pensionRateRepository;

	/** The avg earn level master setting repository. */
	@Inject
	private AvgEarnLevelMasterSettingRepository avgEarnLevelMasterSettingRepository;

	/** The pension avgearn repository. */
	@Inject
	private PensionAvgearnRepository pensionAvgearnRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<RegisterPensionCommand> context) {
		// Get command.
		RegisterPensionCommand command = context.getCommand();

		// Get the current company code.
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());
		OfficeCode officeCode = new OfficeCode(command.getOfficeCode());
		
		// Transfer data
		PensionRate pensionRate = command.toDomain(companyCode);
		pensionRate.validate();
		// Validate
		pensionRateService.validateDateRange(pensionRate);
		pensionRateService.validateRequiredItem(pensionRate);
		pensionRateService.createInitalHistory(companyCode.v(),officeCode.v(), pensionRate.getStart());
		// Insert into db.
		pensionRateRepository.add(pensionRate);

		// Get listAvgEarnLevelMasterSetting.
		List<AvgEarnLevelMasterSetting> listAvgEarnLevelMasterSetting = avgEarnLevelMasterSettingRepository
				.findAll(companyCode);

		List<PensionAvgearn> listPensionAvgearn = listAvgEarnLevelMasterSetting.stream().map(setting -> {
			return new PensionAvgearn(new PensionAvgearnMemento(pensionRate.getHistoryId(), setting,
					pensionRate.getFundRateItems(), pensionRate.getChildContributionRate().v()));
		}).collect(Collectors.toList());

		pensionAvgearnRepository.update(listPensionAvgearn, companyCode.v(), command.getOfficeCode());
	}

	/**
	 * The Class PensionAvgearnMemento.
	 */
	private class PensionAvgearnMemento implements PensionAvgearnGetMemento {

		/** The history id. */
		protected String historyId;

		/** The setting. */
		protected AvgEarnLevelMasterSetting setting;

		/** The rate items. */
		protected Set<FundRateItem> rateItems;

		/** The child contribution rate. */
		protected BigDecimal childContributionRate;

		/**
		 * Instantiates a new pension avgearn memento.
		 *
		 * @param historyId the history id
		 * @param setting the setting
		 * @param rateItems the rate items
		 * @param childContributionRate the child contribution rate
		 */
		public PensionAvgearnMemento(String historyId, AvgEarnLevelMasterSetting setting, Set<FundRateItem> rateItems,
				BigDecimal childContributionRate) {
			this.setting = setting;
			this.rateItems = rateItems;
			this.historyId = historyId;
			this.childContributionRate = childContributionRate;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
		 * PensionAvgearnGetMemento#getHistoryId()
		 */
		@Override
		public String getHistoryId() {
			return this.historyId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
		 * PensionAvgearnGetMemento#getLevelCode()
		 */
		@Override
		public Integer getLevelCode() {
			return setting.getCode();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
		 * PensionAvgearnGetMemento#getChildContributionAmount()
		 */
		@Override
		public InsuranceAmount getChildContributionAmount() {
			return new InsuranceAmount(BigDecimal.valueOf(setting.getAvgEarn()).multiply(childContributionRate));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
		 * PensionAvgearnGetMemento#getCompanyFund()
		 */
		@Override
		public PensionAvgearnValue getCompanyFund() {
			// TODO: Thua truong
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
		 * PensionAvgearnGetMemento#getCompanyFundExemption()
		 */
		@Override
		public PensionAvgearnValue getCompanyFundExemption() {
			return calculateAvgearnValue(BigDecimal.valueOf(setting.getAvgEarn()), this.rateItems, false, true);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
		 * PensionAvgearnGetMemento#getCompanyPension()
		 */
		@Override
		public PensionAvgearnValue getCompanyPension() {
			return calculateAvgearnValue(BigDecimal.valueOf(setting.getAvgEarn()), this.rateItems, false, false);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
		 * PensionAvgearnGetMemento#getPersonalFund()
		 */
		@Override
		public PensionAvgearnValue getPersonalFund() {
			// TODO: Thua truong
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
		 * PensionAvgearnGetMemento#getPersonalFundExemption()
		 */
		@Override
		public PensionAvgearnValue getPersonalFundExemption() {
			return calculateAvgearnValue(BigDecimal.valueOf(setting.getAvgEarn()), this.rateItems, true, true);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
		 * PensionAvgearnGetMemento#getPersonalPension()
		 */
		@Override
		public PensionAvgearnValue getPersonalPension() {
			return calculateAvgearnValue(BigDecimal.valueOf(setting.getAvgEarn()), this.rateItems, true, false);
		}

	}

	/**
	 * Calculate avgearn value.
	 *
	 * @param masterRate the master rate
	 * @param rateItems the rate items
	 * @param isPersonal the is personal
	 * @param isExemption the is exemption
	 * @return the pension avgearn value
	 */
	private PensionAvgearnValue calculateAvgearnValue(BigDecimal masterRate, Set<FundRateItem> rateItems,
			boolean isPersonal, boolean isExemption) {
		PensionAvgearnValue value = new PensionAvgearnValue();
		rateItems.forEach(rateItem -> {
			if (rateItem.getPayType() == PaymentType.Salary) {
				switch (rateItem.getGenderType()) {
				case Female:
					value.setFemaleAmount(
							new CommonAmount(calculateChargeRate(masterRate, rateItem, isPersonal, isExemption)));
					break;
				case Male:
					value.setMaleAmount(
							new CommonAmount(calculateChargeRate(masterRate, rateItem, isPersonal, isExemption)));
					break;
				case Unknow:
					value.setUnknownAmount(
							new CommonAmount(calculateChargeRate(masterRate, rateItem, isPersonal, isExemption)));
					break;
				}
			}
		});

		return value;

	}

	/**
	 * Calculate charge rate.
	 *
	 * @param masterRate the master rate
	 * @param rateItem the rate item
	 * @param isPersonal the is personal
	 * @param isExemption the is exemption
	 * @return the big decimal
	 */
	private BigDecimal calculateChargeRate(BigDecimal masterRate, FundRateItem rateItem, boolean isPersonal,
			boolean isExemption) {
		PensionChargeRateItem chargeRate = isExemption ? rateItem.getExemptionChargeRate()
				: rateItem.getBurdenChargeRate();
		if (isPersonal) {
			return masterRate.multiply(chargeRate.getPersonalRate().v());
		}
		return masterRate.multiply(chargeRate.getCompanyRate().v());
	}
}
