package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.AgreementApprovalComments;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ApprovalStatus;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 申請を承認する
 * 36協定特別条項の適用申請を承認または否認をする
 *
 * @author khai.dh
 */
@Stateless
public class AppApproval {

	@Inject
	private ApproverGetDomainService apprService;

	/**
	 * [1] 変更する
	 * 対象申請を承認また否認に変更する。
	 * 申請を承認する場合、３６協定年月設定または３６協定年度設定を作成する。
	 * @param require @Require
	 * @param applicantId 申請ID
	 * @param approverId 承認者 (社員ID)
	 * @param approvalStatus 承認状態
	 * @param approvalComment 承認コメント
	 * @return AtomTask
	 */
	public AtomTask change(Require require,
									String applicantId,
									String approverId,
									ApprovalStatus approvalStatus,
									Optional<AgreementApprovalComments> approvalComment) {
//		$申請 = require.申請を取得する(申請ID)
//
//		if $申請.isEmpty
//		BusinessException: Msg_1262
//
//				$申請 = $申請.申請を承認する(承認者,承認状態,承認コメント)
//
//		if 承認状態 == 承認
//		if $申請.申請時間.３６協定申請種類 == 1ヶ月
//		$３６協定年月 = ３６協定年月設定#３６協定年月設定($申請.申請対象者
//				,$申請.申請時間.1ヶ月時間.年月度,$申請.申請時間.1ヶ月時間.年月度.1ヶ月時間)
//
//		$既存の３６協定年月 = require.年月設定を取得する($申請.申請対象者
//				,$申請.申請時間.1ヶ月時間.年月度)
//
//		if $申請.申請時間.３６協定申請種類 == 1年間
//		$３６協定年 = ３６協定年度設定#３６協定年度設定($申請.申請対象者,$申請.申請時間.年間時間.年度
//				,$申請.申請時間.年間時間.1年間時間)
//
//		$既存の３６協定年 = require.年設定を取得する($申請.申請対象者,$申請.申請時間.年間時間.年度)
//
//		return AtomTask:
//		require.申請を更新する($申請)
//
//		if $３６協定年月.isPresent
//		if $既存の３６協定年月.isPresent
//		require.年月設定を更新する($３６協定年月)
//		else
//		require.年月設定を追加する($３６協定年月)
//
//		if $３６協定年.isPresent
//		if 既存の３６協定年.isPresent
//		require.年設定を更新する($３６協定年)
//		else
//		require.年設定を追加する($３６協定年)

		return null;
	}

	public static interface Require {
//[R-1] 申請を取得する
//	36協定特別条項の適用申請Repository.get(申請ID)
//
//[R-2] 年月設定を取得する
//	３６協定年月設定Repository.get(社員ID,年月)
//
//[R-3] 年設定を取得する
//	３６協定年度設定Repository.get(社員ID,年度)
//
//[R-4] 申請を更新する
//	36協定特別条項の適用申請Repository.Update(36協定特別条項の適用申請)
//
//[R-5] 年月設定を追加する
//	３６協定年月設定Repository.Insert(３６協定年月設定)
//
//
//[R-6] 年月設定を更新する
//	３６協定年月設定Repository.Update(３６協定年月設定)
//
//[R-7] 年設定を追加する
//	３６協定年度設定Repository.Insert(３６協定年度設定)
//
//[R-8] 年設定を更新する
//	３６協定年度設定Repository.Update(３６協定年度設定)

	}
}
