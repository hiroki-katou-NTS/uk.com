
package nts.uk.screen.at.app.ktgwidget;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
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
		//TODO: chờ domain bên 3Si để code (plan 26/10/2020)
		
		//call 承認処理の利用設定を取得する
		approvedDataWidgetStartDto.setApprovalProcessingUseSetting(approvalProcessingUseSettingRepo.findByCompanyId(companyId).orElse(null));
		
		return approvedDataWidgetStartDto;
	}
}