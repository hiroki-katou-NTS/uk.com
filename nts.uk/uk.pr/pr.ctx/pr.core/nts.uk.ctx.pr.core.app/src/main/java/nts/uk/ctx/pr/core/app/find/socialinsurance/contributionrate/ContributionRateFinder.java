package nts.uk.ctx.pr.core.app.find.socialinsurance.contributionrate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.socialinsurance.contributionrate.dto.ContributionByGradeDto;
import nts.uk.ctx.pr.core.app.find.socialinsurance.contributionrate.dto.ContributionRateDto;
import nts.uk.ctx.pr.core.app.find.socialinsurance.contributionrate.dto.ContributionRateHistoryDto;
import nts.uk.ctx.pr.core.app.find.socialinsurance.contributionrate.dto.SocialInsuranceOfficeDto;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.ContributionRate;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.ContributionRateHistory;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.ContributionRateRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ContributionRateFinder {
	@Inject
	private ContributionRateRepository contributionRateRepository;

	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

	public ContributionRateDto findContributionRateByHistoryID(String historyId) {

		Optional<ContributionRate> contributionRate = contributionRateRepository
				.getContributionRateByHistoryId(historyId);

		if (contributionRate.isPresent()) {
			List<ContributionByGradeDto> contributionByGradeDto = new ArrayList<>();
			contributionByGradeDto = contributionRate.get().getContributionByGrade().stream()
					.map(x -> new ContributionByGradeDto(x.getWelfarePensionGrade(), x.getChildCareContribution().v()))
					.collect(Collectors.toList());

			ContributionRateDto contributionRateDto = new ContributionRateDto(historyId,
					contributionRate.get().getChildContributionRatio().v(),
					contributionRate.get().getAutomaticCalculationCls().value, contributionByGradeDto);
			return contributionRateDto;
		}
		return null;
	}

	public List<SocialInsuranceOfficeDto> findOfficeByCompanyId() {
		List<SocialInsuranceOfficeDto> socialInsuranceDtoList = new ArrayList<>();
		List<SocialInsuranceOffice> socialInsuranceOfficeList = socialInsuranceOfficeRepository
				.findByCid(AppContexts.user().companyId());
		socialInsuranceOfficeList.forEach(office -> {
			Optional<ContributionRateHistory> contributionRateHistory = contributionRateRepository.getContributionRateHistoryByOfficeCode(office.getCode().v());
			socialInsuranceDtoList.add(new SocialInsuranceOfficeDto(office.getCode().v(), office.getName().v(),
					ContributionRateHistoryDto.fromDomainToDto(contributionRateHistory, office.getCode().v())));
		});
		return socialInsuranceDtoList;
	}
}
