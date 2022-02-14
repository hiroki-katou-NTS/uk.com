package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.個人別承認ルートを追加時の既存履歴を補正する
 */
public class UpdateApprovalRootHistoryDomainService {

	/**
	 * [1]登録する
	 * 
	 * @param require
	 * @param sid		社員ID
	 * @param baseDate	基準日
	 * @return Atomtask
	 */
	public static List<AtomTask> register(Require require, String sid, GeneralDate baseDate) {
		List<AtomTask> atomTasks = new ArrayList<>();
		// 基準日後の開始日がある履歴を削除する（開始日＞＝基準日）
		List<PersonApprovalRoot> personApprovalRoots = require.getHistoryWithStartDate(sid, baseDate);
		List<String> approverIds = personApprovalRoots.stream()
				.map(PersonApprovalRoot::getApprovalId)
				.distinct().collect(Collectors.toList());
		if (!approverIds.isEmpty()) {
			atomTasks.add(AtomTask.of(() -> require.deletePersonApprovalRoots(approverIds)));
		}
		// 前の履歴を変更する（条件：基準日を含む → 終了日＝基準日の前日）
		List<PersonApprovalRoot> previousHistories = require.getHistoryWithEndDate(sid, baseDate);
		previousHistories.forEach(hist -> {
			hist.getApprRoot().getHistoryItems().forEach(
					data -> data.changeSpan(new DatePeriod(data.getDatePeriod().start(), baseDate.addDays(-1))));
			atomTasks.add(AtomTask.of(() -> require.updatePersonApprovalRoot(hist)));
		});
		return atomTasks;
	}

	public interface Require {

		// [R-1]基準日後の開始日がある履歴を取得する
		List<PersonApprovalRoot> getHistoryWithStartDate(String sid, GeneralDate baseDate);

		// [R-2] 基準日後の終了日がある履歴を取得する
		List<PersonApprovalRoot> getHistoryWithEndDate(String sid, GeneralDate baseDate);

		// [R-3] 個人別承認ルートをUpdateする
		void updatePersonApprovalRoot(PersonApprovalRoot personApprovalRoot);

		// [R-4]承認IDListから履歴を削除する
		void deletePersonApprovalRoots(List<String> approverIds);
	}
}
