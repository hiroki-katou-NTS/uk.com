package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.opetaionsettings.ApproverOperationSettings;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.opetaionsettings.ItemNameInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.opetaionsettings.OperationMode;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.自分の承認者の運用設定.運用モードを設定する
 *
 */
public class SetOperationModeDomainService {

	/**
	 * [1]変更する
	 * 
	 * @param require
	 * @param cid             会社ID
	 * @param operationMode   運用モード
	 * @param optItemNameInfo 項目の名称情報
	 * @return Atomtask
	 */
	public static List<AtomTask> update(Require require, String cid, OperationMode operationMode,
			Optional<ItemNameInformation> optItemNameInfo) {
		List<AtomTask> atomTasks = new ArrayList<>();
		// 承認ルートを利用する単位を見直し
		Optional<ApprovalSetting> optApprovalSetting = require.getApprovalSetting(cid);
		if (optApprovalSetting.isPresent()) {
			ApprovalSetting approvalSetting = optApprovalSetting.get();
			approvalSetting.changeUnit(operationMode);
			atomTasks.add(AtomTask.of(() -> require.updateApprovalSetting(approvalSetting)));
		} else {
			ApprovalSetting approvalSetting = ApprovalSetting.createForEmployee(cid);
			atomTasks.add(AtomTask.of(() -> require.insertApprovalSetting(approvalSetting)));
		}

		// 自分の承認者の運用設定を変更する
		Optional<ApproverOperationSettings> optOperationSetting = require.getOperationSetting(cid);
		if (optOperationSetting.isPresent()) {
			ApproverOperationSettings operationSetting = optOperationSetting.get();
			operationSetting.setOperationMode(operationMode);
			atomTasks.add(AtomTask.of(() -> require.updateOperationSetting(operationSetting)));
		} else {
			if (!optItemNameInfo.isPresent()) {
				throw new BusinessException("Msg_3311");
			}
			ApproverOperationSettings operationSetting = new ApproverOperationSettings(operationMode, optItemNameInfo.get());
			atomTasks.add(AtomTask.of(() -> require.insertOperationSetting(operationSetting)));
		}
		return atomTasks;
	}

	public interface Require {

		// [R-1]承認設定を取得する
		Optional<ApprovalSetting> getApprovalSetting(String cid);

		// [R-2]承認設定をInsertする
		void insertApprovalSetting(ApprovalSetting domain);

		// [R-3]承認設定をUpdateする
		void updateApprovalSetting(ApprovalSetting domain);

		// [R-4]自分の承認者の運用設定を取得する
		Optional<ApproverOperationSettings> getOperationSetting(String cid);

		// [R-5]自分の承認者の運用設定をInsertする
		void insertOperationSetting(ApproverOperationSettings domain);

		// [R-6]自分の承認者の運用設定をUpdateする
		void updateOperationSetting(ApproverOperationSettings domain);
	}
}
