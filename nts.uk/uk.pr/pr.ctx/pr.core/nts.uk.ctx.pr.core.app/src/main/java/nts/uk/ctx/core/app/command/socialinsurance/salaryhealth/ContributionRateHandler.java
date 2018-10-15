package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.UpdateContributionRateDto;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionByGrade;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRate;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRateRepository;

@Stateless
public class ContributionRateHandler extends CommandHandlerWithResult<UpdateContributionRateDto, List<String>> {
	
	@Inject
	private ContributionRateRepository contributionRateRepository;
	
	@Override
	protected List<String> handle(CommandHandlerContext<UpdateContributionRateDto> context) {
		List<String> response = new ArrayList<>();
		UpdateContributionRateDto command = context.getCommand();
		
		// ドメインモデル「拠出金率」を更新する
		Optional<ContributionRate> contributionRate = contributionRateRepository.getContributionRateByHistoryId(command.getHistoryId(),command.getSocialInsuranceCode());
		if(contributionRate.isPresent()) {
			List<ContributionByGrade> dataUpate = command.getData().stream().map(x -> new ContributionByGrade(x.getWelfarePensionGrade(), new BigDecimal(x.getChildCareContribution()).setScale(2, BigDecimal.ROUND_HALF_EVEN))).collect(Collectors.toList());
			contributionRate.get().updateContributionByGrade(dataUpate);
			List<ContributionByGrade> dataCheck = contributionRate.get().getContributionByGrade();
			if(dataCheck.isEmpty()) {
				contributionRateRepository.insertContributionByGrade(contributionRate.get());
			} else {
				contributionRateRepository.updateContributionByGrade(contributionRate.get());
			}
		}
		response.add("Msg_15");
		return response;
	}

}
