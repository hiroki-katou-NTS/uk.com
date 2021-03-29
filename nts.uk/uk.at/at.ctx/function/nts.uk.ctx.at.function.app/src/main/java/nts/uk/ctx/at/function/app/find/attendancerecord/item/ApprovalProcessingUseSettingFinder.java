package nts.uk.ctx.at.function.app.find.attendancerecord.item;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApprovalProcessingUseSettingFinder {
	
	/** The approval processing use setting repository */
	@Inject
	private ApprovalProcessingUseSettingRepository approvalProcessingUseSettingRepo;
	
	/**
	 * #3803 アルゴリズム「承認処理の利用設定を取得する」
	 * @param companyId 会社ID
	 * @return the approval processing use setting
	 */
	public ApprovalProcessingUseSettingDto getApprovalProcessingUseSettingDto(String companyId) {
		//アルゴリズム「承認処理の利用設定を取得する」を実行する
		Optional<ApprovalProcessingUseSetting> aPUS = this.approvalProcessingUseSettingRepo.findByCompanyId(companyId);
		if (!aPUS.isPresent()) {
			return ApprovalProcessingUseSettingDto.builder().build();
		}

		return ApprovalProcessingUseSettingDto.builder()
				.companyId(aPUS.get().getCompanyId())
				.useDayApproverConfirm(aPUS.get().getUseDayApproverConfirm())
				.useMonthApproverConfirm(aPUS.get().getUseMonthApproverConfirm())
				.lstJobTitleNotUse(aPUS.get().getLstJobTitleNotUse())
				.supervisorConfirmErrorAtr(aPUS.get().getSupervisorConfirmErrorAtr().value)
				.build();
	}
}
