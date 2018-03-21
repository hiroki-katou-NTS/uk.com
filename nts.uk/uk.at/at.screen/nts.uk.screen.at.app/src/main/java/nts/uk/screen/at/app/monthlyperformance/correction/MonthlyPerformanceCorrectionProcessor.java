package nts.uk.screen.at.app.monthlyperformance.correction;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentityProcessUseSetRepository;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceCorrectionDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * TODO
 */
@Stateless
public class MonthlyPerformanceCorrectionProcessor {

	@Inject
	private ApprovalProcessingUseSettingRepository approvalProcessingUseSettingRepository;
	@Inject
	private IdentityProcessUseSetRepository identityProcessUseSetRepository;

	/**
	 * @return TODO
	 */
	public MonthlyPerformanceCorrectionDto generateData() {
		String companyId = AppContexts.user().companyId();
		MonthlyPerformanceCorrectionDto screenDto = new MonthlyPerformanceCorrectionDto();
		//TODO ドメインモデル「実績修正画面で利用するフォーマット」を取得する
		//TODO ドメインモデル「月別実績の修正の機能」を取得する
		screenDto.setApprovalProcessingUseSetting(approvalProcessingUseSettingRepository.findByCompanyId(companyId).orElse(null));
		screenDto.setIdentityProcessUseSet(identityProcessUseSetRepository.findByKey(companyId).orElse(null));
		return screenDto;
	}
}
