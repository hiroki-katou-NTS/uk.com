package nts.uk.screen.at.app.monthlyperformance.correction;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentityProcessUseSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcessRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformanceRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcessRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.MonPerformanceFun;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.MonPerformanceFunRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceCorrectionDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * TODO
 */
@Stateless
public class MonthlyPerformanceCorrectionProcessor {

	@Inject
	private FormatPerformanceRepository formatPerformanceRepository;
	@Inject
	private MonPerformanceFunRepository monPerformanceFunRepository;
	@Inject
	private ApprovalProcessRepository approvalProcessRepository;
	@Inject
	private IdentityProcessRepository identityProcessRepository;
	@Inject
	private ApprovalProcessingUseSettingRepository approvalProcessingUseSettingRepository;
	@Inject
	private IdentityProcessUseSetRepository identityProcessUseSetRepository;
	@Inject
	private EmploymentAdapter employmentAdapter;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	@Inject
	private ShClosurePub shClosurePub;

	/**
	 * @return TODO
	 */
	public MonthlyPerformanceCorrectionDto generateData() {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		AppContexts.user().roles();
		MonthlyPerformanceCorrectionDto screenDto = new MonthlyPerformanceCorrectionDto();
		// ドメインモデル「実績修正画面で利用するフォーマット」を取得する
		Optional<FormatPerformance> formatPerformance = formatPerformanceRepository.getFormatPerformanceById(companyId);
		// ドメインモデル「月別実績の修正の機能」を取得する
		Optional<MonPerformanceFun> monPerformanceFun = monPerformanceFunRepository.getMonPerformanceFunById(companyId);
		// ドメインモデル「承認処理の利用設定」を取得する
		Optional<ApprovalProcess> approvalProcess = approvalProcessRepository.getApprovalProcessById(companyId);
		// ドメインモデル「本人確認処理の利用設定」を取得する
		Optional<IdentityProcess> identityProcess = identityProcessRepository.getIdentityProcessById(companyId);
		// 承認処理の利用設定
		Optional<ApprovalProcessingUseSetting> approvalProcessingUseSetting
				= this.approvalProcessingUseSettingRepository.findByCompanyId(companyId);
		// 本人確認処理の利用設定
		Optional<IdentityProcessUseSet> identityProcessUseSet
				= this.identityProcessUseSetRepository.findByKey(companyId);
		// 社員IDと基準日から社員の雇用コードを取得
		Optional<EmploymentHistoryImported> empHistory
				= this.employmentAdapter.getEmpHistBySid(companyId, employeeId, GeneralDate.today());
		// 雇用に紐づく締めを取得する
		String employmentCode = empHistory.orElseThrow(() -> new BusinessException("Msg_1143")).getEmploymentCode();
		Optional<ClosureEmployment> closureEmployment
				= this.closureEmploymentRepository.findByEmploymentCD(companyId, employmentCode);
		// アルゴリズム「処理年月と締め期間を取得する」を実行する
		Integer closureId = closureEmployment.orElseThrow(() -> new BusinessException("Msg_1143")).getClosureId();
		Optional<PresentClosingPeriodExport> presentClosingPeriodExport = this.shClosurePub.find(companyId, closureId);

		screenDto.setApprovalProcessingUseSetting(approvalProcessingUseSetting.orElse(null));
		screenDto.setIdentityProcessUseSet(identityProcessUseSet.orElse(null));
		screenDto.setPresentClosingPeriodExport(presentClosingPeriodExport.orElse(null));
		return screenDto;
	}
}
