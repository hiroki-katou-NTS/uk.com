package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalSettingInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.opetaionsettings.ApproverOperationSettings;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.自分の承認者設定を取得する
 */
public class GetSelfApprovalSettingsDomainService {

	/**
	 * [1]取得する
	 * 
	 * @param require
	 * @param sid     社員ID
	 * @return 承認者設定情報
	 */
	public static List<ApprovalSettingInformation> get(Require require, String sid) {
		List<ApplicationType> applicationTypes = Arrays.asList(ApplicationType.values());
		List<ConfirmationRootType> confirmationRootTypes = Arrays.asList(ConfirmationRootType.values());

		// 設定を取得する
		Optional<ApproverOperationSettings> optOperationSetting = require.getOperationSetting();
		if (optOperationSetting.isPresent()) {
			applicationTypes = optOperationSetting.get().getApplicationToUse();
			confirmationRootTypes = optOperationSetting.get().getConfirmationToUse();
		}
		List<PersonApprovalRoot> personApprovalRoots = require.getPersonApprovalRoots(sid, GeneralDate.today(),
				applicationTypes, confirmationRootTypes);

		// 基準日を含む履歴がないければ、次の履歴を取得する （開始日（MIN） > 基準日）
		if (personApprovalRoots.isEmpty()) {
			Optional<PersonApprovalRoot> optNextHistory = require.getSmallestHistFromBaseDate(sid, GeneralDate.today(),
					applicationTypes, confirmationRootTypes);
			if (optNextHistory.isPresent()) {
				GeneralDate nextHistBaseDate = optNextHistory.get().getApprRoot().getHistoryItems().stream().findFirst()
						.map(data -> data.getDatePeriod().start()).orElse(null);
				personApprovalRoots = require.getPersonApprovalRoots(sid, nextHistBaseDate, applicationTypes,
						confirmationRootTypes);
			}
		}
		List<String> approverIds = personApprovalRoots.stream().map(data -> data.getApprovalId()).distinct()
				.collect(Collectors.toList());
		List<ApprovalPhase> approvalPhases = require.getApprovalPhases(approverIds);
		List<ApprovalPhase> approvalPhasesFiltered = approvalPhases;

		// 承認フェーズListを整理する
		if (optOperationSetting.isPresent()) {
			approvalPhasesFiltered = optOperationSetting.get().organizeApprovalPhaseToBeUsed(approvalPhases);
		}
		Map<String, List<ApprovalPhase>> approvalPhaseMap = approvalPhasesFiltered.stream()
				.collect(Collectors.groupingBy(ApprovalPhase::getApprovalId));

		// create List<承認者設定情報>
		return personApprovalRoots.stream()
				.map(data -> new ApprovalSettingInformation(approvalPhaseMap.get(data.getApprovalId()), data))
				.collect(Collectors.toList());
	}

	public interface Require {

		// [R-1]社員ID、利用する種類から基準日を含む上長・社員が行う承認ルート設定を取得する
		List<PersonApprovalRoot> getPersonApprovalRoots(String sid, GeneralDate baseDate,
				List<ApplicationType> appTypes, List<ConfirmationRootType> confirmationRootTypes);

		// [R-2]承認IDListから承認フェーズ取得する
		List<ApprovalPhase> getApprovalPhases(List<String> approverIds);

		// [R-3]自分の承認者の運用設定を取得する
		Optional<ApproverOperationSettings> getOperationSetting();

		// [R-4]基準日から一番小さい履歴を取得する
		Optional<PersonApprovalRoot> getSmallestHistFromBaseDate(String sid, GeneralDate baseDate,
				List<ApplicationType> appTypes, List<ConfirmationRootType> confirmTypes);
	}
}
