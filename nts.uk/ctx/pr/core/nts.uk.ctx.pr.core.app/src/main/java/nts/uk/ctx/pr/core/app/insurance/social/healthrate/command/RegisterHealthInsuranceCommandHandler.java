/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthrate.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.core.dom.util.PrimitiveUtil;
import nts.uk.ctx.pr.core.dom.insurance.CalculateMethod;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.avgearn.AvgEarnLevelMasterSetting;
import nts.uk.ctx.pr.core.dom.insurance.avgearn.AvgEarnLevelMasterSettingRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRounding;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.service.HealthInsuranceRateService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RegisterHealthInsuranceCommandHandler.
 */
@Stateless
public class RegisterHealthInsuranceCommandHandler extends CommandHandler<RegisterHealthInsuranceCommand> {

	/** The health insurance rate service. */
	@Inject
	private HealthInsuranceRateService healthInsuranceRateService;

	/** The health insurance rate repository. */
	@Inject
	private HealthInsuranceRateRepository healthInsuranceRateRepository;

	/** The health insurance avgearn repository. */
	@Inject
	private HealthInsuranceAvgearnRepository healthInsuranceAvgearnRepository;

	/** The avg earn level master setting repository. */
	@Inject
	private AvgEarnLevelMasterSettingRepository avgEarnLevelMasterSettingRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<RegisterHealthInsuranceCommand> context) {
		// Get command.
		RegisterHealthInsuranceCommand command = context.getCommand();
		// Get the current company code.
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());
		OfficeCode officeCode = new OfficeCode(command.getOfficeCode());
		List<HealthInsuranceRate> listHealthInsuranceRate = healthInsuranceRateRepository.findAllOffice(companyCode,
				officeCode);
		HealthInsuranceRate healthInsuranceRate = null;
		if (listHealthInsuranceRate.isEmpty()) {
			command.setIsCloneData(false);
		} else {
			healthInsuranceRate = listHealthInsuranceRate.get(0);
		}
		HealthInsuranceRate addNewHealthInsuranceRate = new HealthInsuranceRate(
				this.covertToMemento(healthInsuranceRate, command));

		// Validate
		healthInsuranceRateService.validateDateRange(addNewHealthInsuranceRate);
		healthInsuranceRateService.validateRequiredItem(addNewHealthInsuranceRate);

		// Insert into db.
		healthInsuranceRateRepository.add(addNewHealthInsuranceRate);

		// Get listAvgEarnLevelMasterSetting.
		List<AvgEarnLevelMasterSetting> listAvgEarnLevelMasterSetting = avgEarnLevelMasterSettingRepository
				.findAll(companyCode);

		// Auto calculate listHealthInsuranceAvgearn
		List<HealthInsuranceAvgearn> listHealthInsuranceAvgearn = listAvgEarnLevelMasterSetting.stream()
				.map(setting -> {
					return new HealthInsuranceAvgearn(new HealthInsuranceAvgearnMemento(setting,
							addNewHealthInsuranceRate.getRateItems(), addNewHealthInsuranceRate.getHistoryId()));
				}).collect(Collectors.toList());

		healthInsuranceAvgearnRepository.update(listHealthInsuranceAvgearn, companyCode.v(), command.getOfficeCode());
	}

	protected HealthInsuranceRateGetMemento covertToMemento(HealthInsuranceRate healthInsuranceRate,
			RegisterHealthInsuranceCommand command) {
		Boolean isCloneData = command.getIsCloneData();
		return new HealthInsuranceRateGetMemento() {

			@Override
			public String getHistoryId() {
				return IdentifierUtil.randomUniqueId();
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(AppContexts.user().companyCode());
			}

			@Override
			public OfficeCode getOfficeCode() {
				return new OfficeCode(command.getOfficeCode());
			}

			@Override
			public MonthRange getApplyRange() {
				return MonthRange.range(PrimitiveUtil.toYearMonth(command.getStartMonth(), "/"),
						PrimitiveUtil.toYearMonth(command.getEndMonth(), "/"));
			}

			@Override
			public CalculateMethod getAutoCalculate() {
				if (isCloneData) {
					return healthInsuranceRate.getAutoCalculate();
				} else {
					return CalculateMethod.Auto;
				}
			}

			@Override
			public CommonAmount getMaxAmount() {
				if (isCloneData) {
					return healthInsuranceRate.getMaxAmount();
				} else {
					return new CommonAmount(new BigDecimal(0));
				}
			}

			@Override
			public Set<InsuranceRateItem> getRateItems() {
				if (isCloneData) {
					return healthInsuranceRate.getRateItems();
				} else {
					return command.setDafaultRateItems();
				}
			}

			@Override
			public Set<HealthInsuranceRounding> getRoundingMethods() {
				if (isCloneData) {
					return healthInsuranceRate.getRoundingMethods();
				} else {
					return command.setDafaultRounding();
				}
			}
		};
	}

	private HealthInsuranceAvgearnValue calculateAvgearnValue(BigDecimal masterRate, Set<InsuranceRateItem> rateItems,
			boolean isPersonal) {
		HealthInsuranceAvgearnValue value = new HealthInsuranceAvgearnValue();
		rateItems.forEach(rateItem -> {
			if (rateItem.getPayType() == PaymentType.Salary) {
				switch (rateItem.getInsuranceType()) {
				case Basic:
					value.setHealthBasicMny(new CommonAmount(calculateChargeRate(masterRate, rateItem, isPersonal)));
					break;
				case General:
					value.setHealthGeneralMny(new CommonAmount(calculateChargeRate(masterRate, rateItem, isPersonal)));
					break;
				case Nursing:
					value.setHealthNursingMny(new CommonAmount(calculateChargeRate(masterRate, rateItem, isPersonal)));
					break;
				case Special:
					value.setHealthSpecificMny(new CommonAmount(calculateChargeRate(masterRate, rateItem, isPersonal)));
					break;
				}
			}
		});

		return value;

	}

	private BigDecimal calculateChargeRate(BigDecimal masterRate, InsuranceRateItem rateItem, boolean isPersonal) {
		if (isPersonal) {
			return masterRate.multiply(rateItem.getChargeRate().getPersonalRate().v());
		}
		return masterRate.multiply(rateItem.getChargeRate().getCompanyRate().v());
	}

	private class HealthInsuranceAvgearnMemento implements HealthInsuranceAvgearnGetMemento {
		protected AvgEarnLevelMasterSetting setting;
		protected Set<InsuranceRateItem> rateItems;
		protected String historyId;

		public HealthInsuranceAvgearnMemento(AvgEarnLevelMasterSetting setting, Set<InsuranceRateItem> rateItems,
				String historyId) {
			this.setting = setting;
			this.rateItems = rateItems;
			this.historyId = historyId;
		}

		@Override
		public String getHistoryId() {
			return historyId;
		}

		@Override
		public Integer getLevelCode() {
			return setting.getCode();
		}

		@Override
		public HealthInsuranceAvgearnValue getCompanyAvg() {
			return calculateAvgearnValue(BigDecimal.valueOf(setting.getAvgEarn()), rateItems, false);
		}

		@Override
		public HealthInsuranceAvgearnValue getPersonalAvg() {
			return calculateAvgearnValue(BigDecimal.valueOf(setting.getAvgEarn()), rateItems, true);
		}

	}

}
