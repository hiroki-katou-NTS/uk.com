package nts.uk.screen.com.app.find.cmm030.f;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.pub.employee.ResultRequest600Export;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.ApprovalPhaseDto;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.screen.com.app.find.cmm030.a.dto.PersonApprovalRootDto;
import nts.uk.screen.com.app.find.cmm030.f.dto.SelfApproverSettingDto;
import nts.uk.screen.com.app.find.cmm030.f.dto.SummarizePeriodDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class Cmm030FScreenQuery {

	@Inject
	private PersonApprovalRootRepository personApprovalRootRepository;

	@Inject
	private ApprovalPhaseRepository approvalPhaseRepository;

	@Inject
	private SyEmployeePub syEmployeePub;

	/**
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.F：承認者の履歴確認.メニュー別OCD.Ｆ：承認者の履歴を取得する
	 * 
	 * @param sid 社員ID
	 * @return 開始日、終了日、運用モード、重なるフラグ、承認ID<List>
	 */
	public List<SummarizePeriodDto> getApproverHistory(String sid) {
		SystemAtr systemAtr = SystemAtr.WORK;
		String cid = AppContexts.user().companyId();
		// 全ての社員履歴を取得する(ログイン会社ID、対象社員ID、input.システム区分)
		List<PersonApprovalRoot> domains = this.personApprovalRootRepository.getAllEmpHist(cid, sid, systemAtr);
		// 期間をまとめる
		return this.summarizePeriod(domains);
	}

	/**
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.F：承認者の履歴確認.メニュー別OCD.Ｆ：自分の承認者設定を取得する
	 * 
	 * @param sid			社員ID
	 * @param approvalIds	承認ID<List>
	 * @return
	 */
	public SelfApproverSettingDto getSelfApproverSetting(String sid, List<String> approvalIds) {
		String cid = AppContexts.user().companyId();
		// 承認IDListから承認フェーズ取得する(ログイン会社ID、承認ID<List>)
		List<ApprovalPhase> approvalPhases = this.approvalPhaseRepository.getFromApprovalIds(cid, approvalIds);
		// 社員ID、承認IDListから承認ルート設定を取得する(社員ID、承認ID<List>)
		List<PersonApprovalRoot> personApprovalRoots = this.personApprovalRootRepository.getPsRootBySidAndApprovals(cid,
				sid, approvalIds);
		// [RQ600]社員ID（List）から社員コードと表示名を取得（削除社員考慮）
		List<String> sids = approvalPhases.stream().map(ApprovalPhase::getApprovers).flatMap(List::stream)
				.map(Approver::getEmployeeId).distinct().collect(Collectors.toList());
		List<ResultRequest600Export> employees = this.syEmployeePub.getEmpInfoLstBySids(sids, null, false, false);
		return new SelfApproverSettingDto(
				personApprovalRoots.stream().map(PersonApprovalRootDto::fromDomain).collect(Collectors.toList()),
				approvalPhases.stream().map(ApprovalPhaseDto::fromDomain).collect(Collectors.toList()), employees);
	}

	/**
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.F：承認者の履歴確認.メニュー別OCD.Ｆ：承認者の履歴を取得する.期間をまとめる.期間をまとめる
	 * 
	 * @param domains 個人別承認ルート<List>
	 * @return 開始日、終了日、運用モード、重なるフラグ、承認ID<List>
	 */
	private List<SummarizePeriodDto> summarizePeriod(List<PersonApprovalRoot> domains) {
		// ドメインモデル「個人別承認ルート」「承認ルート履歴」の期間．開始日と期間．終了日と運用モードでグルーピングする
		Map<OverlapKey, List<String>> dataMap = new HashMap<>();
		domains.forEach(data -> {
			data.getApprRoot().getHistoryItems().forEach(hist -> {
				OverlapKey key = new OverlapKey(hist.getDatePeriod().start(), hist.getDatePeriod().end(),
						data.getOperationMode().value);
				if (dataMap.containsKey(key)) {
					dataMap.get(key).add(data.getApprovalId());
				} else {
					dataMap.put(key, Arrays.asList(data.getApprovalId()));
				}
			});
		});
		// グルーピングした期間・運用モードは重なっているかチェックする（期間毎に重なる場合フラグセット）
		return dataMap.entrySet().stream().map(e -> {
			boolean isOverlap = dataMap.keySet().stream().anyMatch(k -> k.isOverlap(e.getKey()));
			return new SummarizePeriodDto(e.getKey().startDate, e.getKey().endDate, e.getKey().operationMode, isOverlap,
					e.getValue());
		}).collect(Collectors.toList());
	}

	@Getter
	@AllArgsConstructor
	private class OverlapKey {

		/**
		 * 期間．開始日
		 */
		private GeneralDate startDate;

		/**
		 * 期間．終了日
		 */
		private GeneralDate endDate;

		/**
		 * 運用モード
		 */
		private int operationMode;

		private DatePeriod getPeriod() {
			return new DatePeriod(this.startDate, this.endDate);
		}

		public boolean isOverlap(OverlapKey other) {
			if (this.operationMode != other.operationMode) {
				return false;
			}
			return this.getPeriod().compare(other.getPeriod()).isDuplicated();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof OverlapKey) {
				OverlapKey o = (OverlapKey) obj;
				return o.startDate.equals(this.startDate) && o.endDate.afterOrEquals(this.endDate)
						&& o.operationMode == this.operationMode;
			}
			return false;
		}
	}
}
