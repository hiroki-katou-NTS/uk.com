package nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.ContributionRateHistory;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.ContributionRateRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthInsuranceFeeRateHistory;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFeeRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.BonusEmployeePensionInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFeeRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassificationRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistory;
import nts.uk.shr.com.context.AppContexts;

@Transactional
@Stateless
public class DeleteSocialOfficeCommandHandler extends CommandHandlerWithResult<FindSocialOfficeCommand,List<String>> {
	
	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	
	@Inject
	private BonusHealthInsuranceRateRepository bonusHealthInsuranceRateRepository;
	
	@Inject
	private HealthInsuranceMonthlyFeeRepository healthInsuranceMonthlyFeeRepository;
	
	@Inject
	private WelfarePensionInsuranceClassificationRepository welfarePensionInsuranceClassificationRepository;
	
	@Inject
	private BonusEmployeePensionInsuranceRateRepository bonusEmployeePensionInsuranceRateRepository;
	
	@Inject
	private EmployeesPensionMonthlyInsuranceFeeRepository employeesPensionMonthlyInsuranceFeeRepository;
	
	@Inject
	private ContributionRateRepository contributionRateRepository;
	
	@Override
	protected List<String> handle(CommandHandlerContext<FindSocialOfficeCommand> context) {
		List<String> response = new ArrayList<>();
		FindSocialOfficeCommand command = context.getCommand();
		Optional<SocialInsuranceOffice> data = socialInsuranceOfficeRepository.findByCodeAndCid(AppContexts.user().companyId(), command.getCode());
		if(data.isPresent()) {
			//socialInsuranceOfficeRepository.remove(AppContexts.user().companyId(), command.getCode());

			// ドメインモデル「賞与健康保険料率」を削除する
			Optional<HealthInsuranceFeeRateHistory> healthInsuranceFeeRateHistory = bonusHealthInsuranceRateRepository.getHealthInsuranceHistoryByOfficeCode(command.getCode());
			List<String> historyIds = new ArrayList<>();
			if(healthInsuranceFeeRateHistory.isPresent()) {
				historyIds = healthInsuranceFeeRateHistory.get().items().stream().map(x -> x.identifier()).collect(Collectors.toList());
			}

			bonusHealthInsuranceRateRepository.deleteByHistoryIds(historyIds);

			// ドメインモデル「健康保険月額保険料額」を削除する
			healthInsuranceMonthlyFeeRepository.deleteByHistoryIds(historyIds);


		}

		// ドメインモデル「厚生年金保険料率履歴」を全て取得する

		Optional<WelfarePensionInsuranceRateHistory> dataWelfare = welfarePensionInsuranceClassificationRepository.getWelfarePensionHistoryByOfficeCode(command.getCode());
		if(dataWelfare.isPresent()) {
			List<String> historyIds = dataWelfare.get().getHistory().stream().map(x ->x.identifier()).collect(Collectors.toList());
			// ドメインモデル「厚生年金保険区分」を削除する
			welfarePensionInsuranceClassificationRepository.deleteByHistoryIds(historyIds);

			// ドメインモデル「賞与厚生年金保険料率」を削除する
			bonusEmployeePensionInsuranceRateRepository.deleteByHistoryIds(historyIds);

			// ドメインモデル「厚生年金月額保険料額」を削除する
			employeesPensionMonthlyInsuranceFeeRepository.deleteByHistoryIds(historyIds);


		}

		// ドメインモデル「拠出金率履歴」を全て取得する
		Optional<ContributionRateHistory> dataContri = contributionRateRepository.getContributionRateHistoryByOfficeCode(command.getCode());
		if(dataContri != null &&  dataContri.isPresent()) {
			List<String> historyIds = dataContri.get().getHistory().stream().map(x -> x.identifier()).collect(Collectors.toList());

			// ドメインモデル「拠出金率」を削除する
			contributionRateRepository.deleteByHistoryIds(historyIds,command.getCode());

		}

		// ドメインモデル「社会保険事業所」を削除する
		socialInsuranceOfficeRepository.remove(AppContexts.user().companyId(), command.getCode());
		response.add(command.getCode());
		return response;

		// 共通アルゴリズム「削除後の選択」を実行する
		
	}
}
