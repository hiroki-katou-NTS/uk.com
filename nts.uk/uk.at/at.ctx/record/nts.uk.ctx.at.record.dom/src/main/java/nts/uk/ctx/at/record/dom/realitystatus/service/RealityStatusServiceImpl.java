package nts.uk.ctx.at.record.dom.realitystatus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.request.service.ApprovalStatusEmployeeImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalStatusForEmployee;
import nts.uk.ctx.at.record.dom.realitystatus.service.output.DailyConfirmOutput;
import nts.uk.ctx.at.record.dom.realitystatus.service.output.SumCountOutput;
import nts.uk.ctx.at.record.dom.realitystatus.service.output.UseSetingOutput;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RealityStatusServiceImpl implements RealityStatusService {
	@Inject
	private ApprovalStatusAdapter approvalStatusAdapter;

	/**
	 * アルゴリズム「承認状況取得実績使用設定」を実行する
	 * 
	 * @return
	 */
	@Override
	public UseSetingOutput getUseSetting() {
		// 月別確認を利用する ← 承認処理の利用設定.月の承認者確認を利用する
		boolean monthlyConfirm = true;
		// 上司確認を利用する ← 承認処理の利用設定.日の承認者確認を利用する
		boolean useBossConfirm = true;
		// 本人確認を利用する ← 本人確認処理の利用設定.日の本人確認を利用する
		boolean usePersonConfirm = true;
		// Waiting for Q&A
		return new UseSetingOutput(monthlyConfirm, useBossConfirm, usePersonConfirm);
	}

	/**
	 * アルゴリズム「承認状況取得職場実績確認」を実行する
	 * 
	 * @param listEmp
	 *            社員ID＜社員ID、期間＞(リスト)
	 * @param wkpId
	 *            職場ID
	 * @param useSeting
	 *            月別確認を利用する/上司確認を利用する/本人確認を利用する
	 * @return
	 */
	@Override
	public SumCountOutput getApprovalSttConfirmWkpResults(List<ApprovalStatusEmployeeImport> listEmp, String wkpId,
			UseSetingOutput useSeting) {
		SumCountOutput sumCount = new SumCountOutput();
		// 会社ID
		String cid = AppContexts.user().companyId();

		// 社員ID(リスト)
		for (ApprovalStatusEmployeeImport emp : listEmp) {
			// 月別確認を利用する
			if (useSeting.isMonthlyConfirm()) {
				// Request list 113
				// imported(申請承認）「上司確認の状況」を取得する
				List<ApproveRootStatusForEmpImport> listApproval = approvalStatusAdapter
						.getApprovalByEmplAndDate(emp.getClosureStart(), emp.getClosureEnd(), emp.getSId(), cid, 2);
				ApproveRootStatusForEmpImport approval = listApproval.stream().findFirst().get();
				// 承認状況＝「承認済」の場合(trạng thái approval = 「承認済」)
				if (ApprovalStatusForEmployee.APPROVED.equals(approval.getApprovalStatus())) {
					// 月別確認件数 ＝ ＋１
					sumCount.monthConfirm++;
				}
				// 承認状況＝「未承認 」又は「承認中」の場合
				else {
					// 月別未確認件数 ＝ ＋１
					sumCount.monthUnconfirm++;
				}
			}

			// アルゴリズム「承認状況取得日別確認状況」を実行する
			this.getApprovalSttByDate(emp.getSId(), wkpId, emp.getClosureStart(), emp.getClosureEnd(),
					useSeting.isUseBossConfirm(), useSeting.isUsePersonConfirm(), sumCount);
			// 各件数を合算する

		}
		return sumCount;
	}

	/**
	 * アルゴリズム「承認状況取得日別確認状況」を実行する
	 * 
	 * @param sId
	 *            社員ID
	 * @param wkpId
	 *            職場ID
	 * @param period
	 *            期間(開始日～終了日)
	 * @param useBossConfirm
	 *            上司確認を利用する
	 * @param usePersonConfirm
	 *            本人確認を利用する
	 * @param sumCount
	 *            output
	 */
	private void getApprovalSttByDate(String sId, String wkpId, GeneralDate startDate, GeneralDate endDate,
			boolean useBossConfirm, boolean usePersonConfirm, SumCountOutput sumCount) {
		// 期間範囲分の日別確認（リスト）を作成する(Tạo list confirm hàng ngày)
		List<DailyConfirmOutput> listDailyConfirm = new ArrayList<DailyConfirmOutput>();

		// 利用するの場合(use)
		if (useBossConfirm) {
			// アルゴリズム「承認状況取得日別上司承認状況」を実行する
			this.getApprovalSttByDateOfBoss(sId, wkpId, startDate, endDate, listDailyConfirm, sumCount);
		}
		// 利用しないの場合

		// 利用するの場合(use)
		if (usePersonConfirm) {
			// アルゴリズム「承認状況取得日別本人確認状況」を実行する
			this.getApprovalSttByDateOfPerson(sId, wkpId, startDate, endDate, listDailyConfirm, sumCount);
		}
		// 利用しないの場合
	}

	/**
	 * アルゴリズム「承認状況取得日別上司承認状況」を実行する
	 * 
	 * @param sId
	 *            社員ID
	 * @param wkpId
	 *            職場ID
	 * @param period
	 *            期間(開始日～終了日)
	 * @param listDailyConfirm
	 *            output 日別確認（リスト）＜職場ID、社員ID、対象日、本人確認、上司確認＞
	 * @param sumCount
	 *            output 上司確認件数,上司未確認件数
	 */
	private void getApprovalSttByDateOfBoss(String sId, String wkpId, GeneralDate startDate, GeneralDate endDate,
			List<DailyConfirmOutput> listDailyConfirm, SumCountOutput sumCount) {
		// 会社ID
		String cid = AppContexts.user().companyId();
		// imported（ワークフロー）「承認ルート状況」を取得する
		List<ApproveRootStatusForEmpImport> listApproval = approvalStatusAdapter.getApprovalByEmplAndDate(startDate,
				endDate, sId, cid, 0);
		// 承認ルートの状況
		for (ApproveRootStatusForEmpImport approval : listApproval) {
			// 日別確認（リスト）に同じ職場ID、社員ID、対象日が登録済
			Optional<DailyConfirmOutput> confirm = listDailyConfirm.stream().filter(x -> x.getWkpId().equals(wkpId)
					&& x.getSId().equals(sId) && x.getTargetDate().equals(approval.getAppDate())).findFirst();
			// 登録されていない場合
			if (!confirm.isPresent()) {
				// 日別確認（リスト）に追加する
				DailyConfirmOutput newDailyConfirm = new DailyConfirmOutput(wkpId, sId, approval.getAppDate(), false,
						false);
				listDailyConfirm.add(newDailyConfirm);
			}

			// 承認ルート状況.承認状況
			if (ApprovalStatusForEmployee.APPROVED.equals(approval.getApprovalStatus())) {
				// 「承認済」の場合
				// 上司確認件数 ＝＋１
				sumCount.bossConfirm++;
			} else {
				// 「未承認」又は「承認中」の場合
				// 上司未確認件数 ＝＋１
				sumCount.bossUnconfirm++;
			}
		}
	}

	/**
	 * アルゴリズム「承認状況取得日別本人確認状況」を実行する
	 * 
	 * @param sId
	 *            社員ID
	 * @param wkpId
	 *            職場ID
	 * @param period
	 *            期間(開始日～終了日)
	 * @param listDailyConfirm
	 *            output 日別確認（リスト）＜職場ID、社員ID、対象日、本人確認、上司確認＞
	 * @param sumCount
	 *            output
	 */
	private void getApprovalSttByDateOfPerson(String sId, String wkpId, GeneralDate startDate, GeneralDate endDate,
			List<DailyConfirmOutput> listDailyConfirm, SumCountOutput sumCount) {
		// RequestList165
		// imported（KAF018承認状況の照会）本人確認済みの日付を取得
		List<GeneralDate> listCheckDate = new ArrayList<GeneralDate>();
		// 確認日付
		for (GeneralDate checkDate : listCheckDate) {
			// 日別確認（リスト）に同じ職場ID、社員ID、対象日が登録済
			Optional<DailyConfirmOutput> confirm = listDailyConfirm.stream().filter(
					x -> x.getWkpId().equals(wkpId) && x.getSId().equals(sId) && x.getTargetDate().equals(checkDate))
					.findFirst();
			if (!confirm.isPresent()) {
				// 登録されていない場合(Chưa đăng ký)
				DailyConfirmOutput newDailyConfirm = new DailyConfirmOutput(wkpId, sId, checkDate, true, false);
				listDailyConfirm.add(newDailyConfirm);
			} else {
				// 登録されている場合
				// 対象の日別確認（リスト）の行を対象として内容を更新するする
				confirm.get().setPersonConfirm(true);
			}
			// 本人確認件数 ＝＋１
			sumCount.personConfirm++;
		}
		// 本人未確認件数 ＝日別確認(リスト)で上司確認＝未確認、本人確認＝未確認の件数
		sumCount.personUnconfirm = (int) listDailyConfirm.stream()
				.filter(x -> !x.isBossConfirm() && !x.isPersonConfirm()).count();
	}
}
