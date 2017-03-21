/******************************************************************

 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.service.internal;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryRepository;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.avgearn.AvgEarnLevelMasterSetting;
import nts.uk.ctx.pr.core.dom.insurance.avgearn.AvgEarnLevelMasterSettingRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.service.PensionRateService;

/**
 * The Class PensionRateServiceImpl.
 */
@Stateless
public class PensionRateServiceImpl extends PensionRateService {

	/** The fund rate item count. */
	private final int FUND_RATE_ITEM_COUNT = 6;

	/** The rounding method count. */
	private final int ROUNDING_METHOD_COUNT = 2;

	/** The pension rate repo. */
	@Inject
	private PensionRateRepository pensionRateRepo;
	
	/** The avg earn level master setting repository. */
	@Inject
	private AvgEarnLevelMasterSettingRepository avgEarnLevelMasterSettingRepository;

	/** The pension rate repo. */
	@Inject
	private PensionAvgearnRepository pensionAvgearnRepository;
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.service.
	 * PensionRateService#validateRequiredItem(nts.uk.ctx.pr.core.dom.insurance.
	 * social.pensionrate.PensionRate)
	 */
	@Override
	public void validateRequiredItem(PensionRate rate) {
		if (rate.getOfficeCode() == null || StringUtil.isNullOrEmpty(rate.getOfficeCode().v(), true)
				|| rate.getApplyRange() == null || rate.getMaxAmount() == null
				|| rate.getChildContributionRate() == null || CollectionUtil.isEmpty(rate.getFundRateItems())
				|| rate.getFundRateItems().size() != FUND_RATE_ITEM_COUNT || CollectionUtil.isEmpty(rate.getRoundingMethods())
				|| rate.getRoundingMethods().size() != ROUNDING_METHOD_COUNT) {
			throw new BusinessException("ER001");
		}
	}

	@Override
	public SimpleHistoryRepository<PensionRate> getRepository() {
		return this.pensionRateRepo;
	}

	@Override
	public PensionRate createInitalHistory(String companyCode, String officeCode, YearMonth startTime) {
		List<PensionRate> listPensionOfOffice = this.pensionRateRepo.findAllOffice(companyCode,officeCode);
		List<PensionRate> lstPensionRate = listPensionOfOffice.stream().filter(c -> c.getStart().equals(startTime))
				.collect(Collectors.toList());
		if (!lstPensionRate.isEmpty()) {
			throw new BusinessException("ER011");
		}
		return PensionRate.createWithIntial(new CompanyCode(companyCode), new OfficeCode(officeCode),
				startTime);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryBaseService#
	 * onCopyHistory(java.lang.String, java.lang.String,
	 * nts.uk.ctx.pr.core.dom.base.simplehistory.History,
	 * nts.uk.ctx.pr.core.dom.base.simplehistory.History)
	 */
	@Override
	protected void onCopyHistory(String companyCode, String masterCode, PensionRate copiedHistory,
			PensionRate newHistory) {
		super.onCopyHistory(companyCode, masterCode, copiedHistory, newHistory);
		// Get listAvgEarn of copiedHistory.
		List<PensionAvgearn> listPensionAvgearn = pensionAvgearnRepository
				.find(copiedHistory.getHistoryId());
		// Update newHistoryId.
		List<PensionAvgearn> updatedList = listPensionAvgearn.stream().map(item -> {
			return item.copyWithNewHistoryId(newHistory.getHistoryId());
		}).collect(Collectors.toList());

		this.pensionAvgearnRepository.update(updatedList, companyCode, newHistory.getOfficeCode().v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryBaseService#
	 * onCreateHistory(java.lang.String, java.lang.String,
	 * nts.uk.ctx.pr.core.dom.base.simplehistory.History)
	 */
	@Override
	protected void onCreateHistory(String companyCode, String masterCode, PensionRate newHistory) {
		super.onCreateHistory(companyCode, masterCode, newHistory);
		// Get listAvgEarnLevelMasterSetting.
		List<AvgEarnLevelMasterSetting> listAvgEarnLevelMasterSetting = avgEarnLevelMasterSettingRepository
				.findAll(new CompanyCode(companyCode));
		// Create HealthInsuranceAvgearn list with initial values.
		List<PensionAvgearn> newList = listAvgEarnLevelMasterSetting.stream().map(setting -> {
			return PensionAvgearn.createWithIntial(newHistory.getHistoryId(), setting.getCode());
		}).collect(Collectors.toList());

		this.pensionAvgearnRepository.update(newList, companyCode, newHistory.getOfficeCode().v());
	}
}
