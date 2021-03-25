
package nts.uk.screen.at.app.ktgwidget;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.screen.at.app.ktgwidget.find.dto.ApprovedDataWidgetStartDto;
import nts.uk.screen.at.app.ktgwidget.ktg001.ApprovedDataExecutionFinder;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG001_承認すべきデータ.ユースケース.起動する.起動する
 * 
 * @author tutt
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class KTG001QueryProcessor_ver04 {

	@Inject
	private ApprovalProcessingUseSettingRepository approvalProcessingUseSettingRepo;

	@Inject
	private ApprovedDataExecutionFinder finder;
	
	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepo;
	
	/**
	 * 起動する
	 * 「承認すべきデータ」ウィジェットを起動する
	 */
	public ApprovedDataWidgetStartDto getApprovedDataWidgetStart(Integer yearMonth, int closureId) {
		ApprovedDataWidgetStartDto approvedDataWidgetStartDto = new ApprovedDataWidgetStartDto();
		String companyId = AppContexts.user().companyId();
		
		//call 承認すべきデータのウィジェットを起動する
		approvedDataWidgetStartDto.setApprovedDataExecutionResultDto(finder.getApprovedDataExecutionResult(yearMonth, closureId));
		
		//ドメインモデル「３６協定運用設定」を取得する
		Optional<AgreementOperationSetting>agreementOperationSettingOpt = agreementOperationSettingRepo.find(companyId);

		if (agreementOperationSettingOpt.isPresent()) {
			approvedDataWidgetStartDto.setAgreementOperationSetting(agreementOperationSettingOpt.get());
		} else {
			approvedDataWidgetStartDto.setAgreementOperationSetting(new AgreementOperationSetting("", null, null, false, false));
		}
		
		//call 承認処理の利用設定を取得する
		approvedDataWidgetStartDto.setApprovalProcessingUseSetting(approvalProcessingUseSettingRepo.findByCompanyId(companyId).orElse(null));
		
		return approvedDataWidgetStartDto;
	}
}