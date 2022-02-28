package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalSettingInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentAppHistoryItem;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApprovalRootInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApprovalSettingParam;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApproverInformation;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.個人別承認ルートを複写する
 */
public class CopyPersonalApprovalRootDomainService {

	/**
	 * [1]複写する
	 * 
	 * @param require
	 * @param cid       会社ID
	 * @param sourceSid 複写元の社員ID
	 * @param targetSid 複写先の社員ID
	 * @param baseDate  基準日
	 * @return Atomtask
	 */
	public static AtomTask copy(Require require, String cid, String sourceSid, String targetSid, GeneralDate baseDate) {
		// ①複写元さんの履歴を取得する
		List<ApprovalSettingInformation> sourceInfos = require.getApprovalInfos(cid, sourceSid, baseDate);
		// ②承認者設定パラメータを作成する
		List<ApprovalSettingParam> params = sourceInfos.stream().map(ApprovalSettingInformation::getPersonApprovalRoot)
				.map(data -> {
					Optional<EmploymentAppHistoryItem> histItem = data.getApprRoot().getHistoryItems().stream()
							.findFirst();
					ApprovalRootInformation approvalRootInfo = new ApprovalRootInformation(
							data.getApprRoot().getEmploymentRootAtr(),
							histItem.map(EmploymentAppHistoryItem::getDatePeriod).orElse(null),
							data.getApprRoot().getApplicationType(),
							data.getApprRoot().getConfirmationRootType());
					List<ApproverInformation> approvalPhases = sourceInfos.stream()
							.map(ApprovalSettingInformation::getApprovalPhases).flatMap(List::stream)
							.filter(x -> x.getApprovalId().equals(data.getApprovalId()))
							.map(x -> new ApproverInformation(x.getPhaseOrder(), x.getApprovalId()))
							.collect(Collectors.toList());
					return new ApprovalSettingParam(approvalPhases, approvalRootInfo);
				}).collect(Collectors.toList());
		// ③複写先の履歴を登録する
		return AtomTask
				.of(() -> CreateSelfApprovalRootDomainService.register(require, cid, targetSid, baseDate, params));
	}

	public interface Require extends CreateSelfApprovalRootDomainService.Require {

		// [R-1]基準日を含む以降の社員履歴を取得する
		List<ApprovalSettingInformation> getApprovalInfos(String cid, String sid, GeneralDate baseDate);

		// [R-2]承認IDListから承認フェーズ取得する
		List<ApprovalPhase> getApprovalPhases(String cid, List<String> approvalIds);
	}
}
