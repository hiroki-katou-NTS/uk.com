package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApprovalSettingParam;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.承認ルートを新規登録する（自分の承認者）
 */
public class CreateSelfApprovalRootDomainService {

	/**
	 * [1]新規登録する
	 * 
	 * @param require
	 * @param cid      会社ID
	 * @param sid      社員ID
	 * @param baseDate 基準日
	 * @param params   承認者設定パラメータList
	 * @return
	 */
	public static List<AtomTask> register(Require require, String cid, String sid, GeneralDate baseDate,
			List<ApprovalSettingParam> params) {
		List<AtomTask> atomTasks = new ArrayList<>();
		// 個人別承認ルートを追加時の既存履歴を補正する
		atomTasks.addAll(UpdateApprovalRootHistoryDomainService.register(require, sid, baseDate));
		// 個人別承認ルートを登録する
		atomTasks.addAll(params.stream()
				.map(param -> CreatePersonalApprovalRootDomainService.createAndRegister(require, cid, sid, param))
				.collect(Collectors.toList()));
		// 指定社員の中間データを作成する
		if (baseDate.beforeOrEquals(GeneralDate.today())) {
			CreateEmployeeInterimDataDomainService.create(require, cid, sid, baseDate);
		}
		return atomTasks;
	}

	public interface Require extends UpdateApprovalRootHistoryDomainService.Require,
			CreatePersonalApprovalRootDomainService.Require, CreateEmployeeInterimDataDomainService.Require {

	}
}
