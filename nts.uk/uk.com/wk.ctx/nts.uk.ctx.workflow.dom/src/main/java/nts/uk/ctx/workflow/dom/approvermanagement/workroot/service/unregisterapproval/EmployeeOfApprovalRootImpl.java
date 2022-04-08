package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmployeeImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.*;
import nts.uk.ctx.workflow.dom.service.CollectApprovalRootService;
import nts.uk.ctx.workflow.dom.service.output.LevelOutput;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class EmployeeOfApprovalRootImpl implements EmployeeOfApprovalRoot{
	@Inject
	private CollectApprovalRootService collectApprSv;
	@Inject
	private ApprovalPhaseRepository approvalPhase;

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UnregisteredApprovalCheckResult lstEmpApprovalRoot(String companyId,
									  List<CompanyApprovalRoot> lstCompanyRootInfor,
									  List<WorkplaceApprovalRoot> lstWorkpalceRootInfor,
									  List<PersonApprovalRoot> lstPersonRootInfor,
									  EmployeeImport empInfor,
									  GeneralDate baseDate,
									  int sysAtr,
									  EmploymentRootAtr rootAtr,
									  String appTarget) {
		{
			// Input．ドメインモデル「個人別承認ルート」を取得する
			// 【Loop中の「社員、承認ルート区分、対象申請」を条件に個人別承認ルートを取得する】
			List<PersonApprovalRoot> lstPersonRoot = lstPersonRootInfor.stream().filter(i -> {
				return i.getEmployeeId().equals(empInfor.getSId())
						&& i.getApprRoot().getEmploymentRootAtr() == rootAtr
						&& checkPersonAppRoot(i.getApprRoot(), rootAtr, appTarget);
			}).collect(Collectors.toList());
			if (!lstPersonRoot.isEmpty()) {
				List<ErrorContent> errorList = validateApproverOfApprovalRoot(
						companyId,
						empInfor.getSId(),
						baseDate,
						lstPersonRoot.stream().map(PersonApprovalRoot::getApprovalId).collect(Collectors.toList()),
						sysAtr,
						Optional.empty()
				);
				if (errorList.isEmpty()) return null;
				return new UnregisteredApprovalCheckResult(1, false, Optional.empty(), errorList);
			}

			// Input．ドメインモデル「個人別承認ルート」を取得する
			// 【承認ルート区分＝「共通」に変えて「対象申請」は条件無しで個人別承認ルートを取得する】
			lstPersonRoot = lstPersonRootInfor.stream().filter(i -> {
				return i.getEmployeeId().equals(empInfor.getSId())
						&& i.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.COMMON
						&& i.getApprRoot().getSysAtr().value == sysAtr;
			}).collect(Collectors.toList());
			if (!lstPersonRoot.isEmpty()) {
				List<ErrorContent> errorList = validateApproverOfApprovalRoot(
						companyId,
						empInfor.getSId(),
						baseDate,
						lstPersonRoot.stream().map(PersonApprovalRoot::getApprovalId).collect(Collectors.toList()),
						sysAtr,
						Optional.empty()
				);
				if (errorList.isEmpty()) return null;
				return new UnregisteredApprovalCheckResult(1, true, Optional.empty(), errorList);
			}
		}

		{
			// 対象者の所属職場・部門を含める上位職場・部門を取得する
			List<String> wkpIds = collectApprSv.getUpperIDIncludeSelf(companyId, empInfor.getSId(), baseDate, EnumAdaptor.valueOf(sysAtr, SystemAtr.class));
			for (int i = 0; i < wkpIds.size(); i++) {
				String wkpId = wkpIds.get(i);
				// Input．ドメインモデル「職場別承認ルート」を取得する
				// 【Loop中の「承認ルート区分、対象申請」を条件に職場別承認ルートを取得する】
				List<WorkplaceApprovalRoot> lstWorkpalceRoot = lstWorkpalceRootInfor.stream().filter(w -> {
					return w.getWorkplaceId().equals(wkpId)
							&& w.getApprRoot().getEmploymentRootAtr() == rootAtr
							&& checkPersonAppRoot(w.getApprRoot(), rootAtr, appTarget);
				}).collect(Collectors.toList());
				if (!lstWorkpalceRoot.isEmpty()) {
					List<ErrorContent> errorList = validateApproverOfApprovalRoot(
							companyId,
							empInfor.getSId(),
							baseDate,
							lstWorkpalceRoot.stream().map(WorkplaceApprovalRoot::getApprovalId).collect(Collectors.toList()),
							sysAtr,
							Optional.empty()
					);
					if (errorList.isEmpty()) return null;
					return new UnregisteredApprovalCheckResult(2, false, Optional.of(wkpId), errorList);
				}

				// Input．ドメインモデル「職場別承認ルート」を取得する
				// 【承認ルート区分＝「共通」に変えて「対象申請」は条件無しで職場別承認ルートを取得する】
				lstWorkpalceRoot = lstWorkpalceRootInfor.stream().filter(w -> {
					return w.getWorkplaceId().equals(wkpId)
							&& w.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.COMMON
							&& w.getApprRoot().getSysAtr().value == sysAtr;
				}).collect(Collectors.toList());
				if (!lstWorkpalceRoot.isEmpty()) {
					List<ErrorContent> errorList = validateApproverOfApprovalRoot(
							companyId,
							empInfor.getSId(),
							baseDate,
							lstWorkpalceRoot.stream().map(WorkplaceApprovalRoot::getApprovalId).collect(Collectors.toList()),
							sysAtr,
							Optional.empty()
					);
					if (errorList.isEmpty()) return null;
					return new UnregisteredApprovalCheckResult(2, true, Optional.of(wkpId), errorList);
				}
			}
		}

		{
			// Input．ドメインモデル「会社別承認ルート」を取得する
			// 【Loop中の「承認ルート区分、対象申請」を条件に会社別承認ルートを取得する】
			List<CompanyApprovalRoot> lstCompanyRoot = lstCompanyRootInfor.stream().filter(i -> {
				return i.getCompanyId().equals(companyId)
						&& i.getApprRoot().getEmploymentRootAtr() == rootAtr
						&& checkPersonAppRoot(i.getApprRoot(), rootAtr, appTarget);
			}).collect(Collectors.toList());
			if (!lstCompanyRoot.isEmpty()) {
				List<ErrorContent> errorList = validateApproverOfApprovalRoot(
						companyId,
						empInfor.getSId(),
						baseDate,
						lstCompanyRoot.stream().map(CompanyApprovalRoot::getApprovalId).collect(Collectors.toList()),
						sysAtr,
						Optional.empty()
				);
				if (errorList.isEmpty()) return null;
				return new UnregisteredApprovalCheckResult(3, false, Optional.empty(), errorList);
			}

			// Input．ドメインモデル「会社別承認ルート」を取得する
			// 【承認ルート区分＝「共通」に変えて「相性申請」は条件無しで会社別承認ルートを取得する】
			lstCompanyRoot = lstCompanyRootInfor.stream().filter(i -> {
				return i.getCompanyId().equals(companyId)
						&& i.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.COMMON
						&& i.getApprRoot().getSysAtr().value == sysAtr;
			}).collect(Collectors.toList());
			if (!lstCompanyRoot.isEmpty()) {
				List<ErrorContent> errorList = validateApproverOfApprovalRoot(
						companyId,
						empInfor.getSId(),
						baseDate,
						lstCompanyRoot.stream().map(CompanyApprovalRoot::getApprovalId).collect(Collectors.toList()),
						sysAtr,
						Optional.empty()
				);
				if (errorList.isEmpty()) return null;
				return new UnregisteredApprovalCheckResult(3, true, Optional.empty(), errorList);
			}
		}

		return new UnregisteredApprovalCheckResult();
	}

	private boolean checkPersonAppRoot(ApprovalRoot approvalRoot, EmploymentRootAtr rootAtr, String appTarget) {
		switch (rootAtr) {
			case APPLICATION:
				return approvalRoot.getApplicationType() != null
						&& approvalRoot.getApplicationType().value.toString().equals(appTarget);
			case CONFIRMATION:
				return approvalRoot.getConfirmationRootType() != null
						&& approvalRoot.getConfirmationRootType().value.toString().equals(appTarget);
			case NOTICE:
				return approvalRoot.getNoticeId() != null
						&& approvalRoot.getNoticeId().toString().equals(appTarget);
			case BUS_EVENT:
				return approvalRoot.getBusEventId() != null
						&& approvalRoot.getBusEventId().equals(appTarget);
			default:
				return false;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ErrorContent> validateApproverOfApprovalRoot(String companyId,
												  String employeeId,
												  GeneralDate baseDate,
												  List<String> approvalIds,
												  int sysAtr,
												  Optional<Boolean> lowerApproval) {
		List<ApprovalPhase> phases = approvalPhase.getAllApprovalPhaseByListId(approvalIds);

		// 共通アルゴリズム「承認ルートを整理する」を実行する
		LevelOutput output = collectApprSv.organizeApprovalRoute(
				companyId,
				employeeId,
				baseDate,
				phases,
				EnumAdaptor.valueOf(sysAtr, SystemAtr.class),
				lowerApproval
		);

		// 共通アルゴリズム「承認ルートの異常チェック」を実行する
		List<ErrorContent> result = collectApprSv.checkApprovalRootSequentially(output);

		return result;
	}
}
