package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.AgreementApprovalComments;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 申請を確認する
 * 36協定特別条項の適用申請を確認または否認をする
 *
 * @author khai.dh
 */
@Stateless
public class AppConfirm {

	/**
	 * [1] 変更する
	 * 対象申請を確認または否認をする
	 * 
	 * @param require @Require
	 * @param applicantId 申請ID
	 * @param confirmerId 確認者 (社員ID)
	 * @param confirmStatus 確認状態
	 * @param approvalComment 承認コメント
	 * @return AtomTask
	 */
	public AtomTask change(Require require,
									String applicantId,
									String confirmerId,
									boolean confirmStatus,
									Optional<AgreementApprovalComments> approvalComment) {
//		$申請 = require.申請を取得する(申請ID)
//
//		if $申請.isEmpty
//		BusinessException: Msg_1262
//
//				$申請 = $申請.申請を確認する(確認者,確認状態)
//
//		return AtomTask:
//		require.申請を更新する($申請)

		return null;
	}

	public static interface Require {
//[R-1] 申請を取得する
//	36協定特別条項の適用申請Repository.get(申請ID)
//
//[R-2] 申請を更新する
//	36協定特別条項の適用申請Repository.Update(36協定特別条項の適用申請)
	}
}
