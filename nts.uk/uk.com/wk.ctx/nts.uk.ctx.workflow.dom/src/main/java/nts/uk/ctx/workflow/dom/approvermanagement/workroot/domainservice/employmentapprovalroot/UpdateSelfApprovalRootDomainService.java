package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot;

import java.util.ArrayList;
import java.util.List;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApprovalSettingParam;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.承認ルートを変更登録する（自分の承認者）
 */
public class UpdateSelfApprovalRootDomainService {

	/**
	 * [1]変更登録する
	 * 
	 * @param require
	 * @param cid     会社ID
	 * @param sid     社員ID
	 * @param params  承認者設定パラメータList
	 * @param period  期間
	 * @return Atomtask
	 */
	public static List<AtomTask> register(Require require, String cid, String sid, List<ApprovalSettingParam> params,
			DatePeriod period) {
		List<AtomTask> atomTasks = new ArrayList<>();
		// 個人別承認ルートを登録する
		atomTasks.addAll(ChangePersonalApprovalRootDomainService.create(require, cid, sid, period, params));
		// 指定社員の中間データを作成する
		if (period.start().beforeOrEquals(GeneralDate.today())) {
			CreateEmployeeInterimDataDomainService.create(require, cid, sid, period.start());
		}
		return atomTasks;
	}

	public interface Require
			extends CreateEmployeeInterimDataDomainService.Require, ChangePersonalApprovalRootDomainService.Require {

	}
}
