package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalSettingInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApprovalSettingParam;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.個人別承認ルートを変更する
 */
public class ChangePersonalApprovalRootDomainService {

	/**
	 * [1]変更する
	 * 
	 * @param require
	 * @param cid        会社ID
	 * @param sid        社員ID
	 * @param period     期間
	 * @param parameters 承認者設定パラメータList
	 * @return Atomtask
	 */
	public static List<AtomTask> create(Require require, String cid, String sid, DatePeriod period,
			List<ApprovalSettingParam> params) {
		List<AtomTask> atomTasks = new ArrayList<>();
		// DBに古いデータを削除する
		List<PersonApprovalRoot> personApprovalRoots = require.getPersonApprovalRoots(cid, sid, period);
		List<String> approverIds = personApprovalRoots.stream()
				.map(PersonApprovalRoot::getApprovalId).distinct().collect(Collectors.toList());
		if (!approverIds.isEmpty()) {
			atomTasks.add(AtomTask.of(() -> require.deletePersonApprovalRoots(approverIds)));
		}
		// 新しいデータを登録する
		params.forEach(param -> {
			ApprovalSettingInformation data = CreatePersonalApprovalRootDomainService.createData(cid, sid, param);
			atomTasks.add(AtomTask.of(() -> require.insertAll(data.getPersonApprovalRoot(), data.getApprovalPhases())));
		});
		return atomTasks;
	}

	public interface Require extends CreateEmployeeInterimDataDomainService.Require {
		// [R-1]期間から履歴を取得する
		List<PersonApprovalRoot> getPersonApprovalRoots(String cid, String sid, DatePeriod period);

		// [R-2]承認IDListから履歴を削除する
		void deletePersonApprovalRoots(List<String> approvalIds);

		// [R-3]個人別承認ルートと承認フェーズを insertする()
		void insertAll(PersonApprovalRoot personApprovalRoot, List<ApprovalPhase> approvalPhases);
	}
}
