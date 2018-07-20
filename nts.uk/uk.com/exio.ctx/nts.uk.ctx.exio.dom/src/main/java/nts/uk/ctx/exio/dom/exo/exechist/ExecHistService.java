package nts.uk.ctx.exio.dom.exo.exechist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exo.adapter.bs.employee.PersonInfoAdapter;
import nts.uk.ctx.exio.dom.exo.adapter.bs.employee.PersonInfoImport;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExternalOutputCategory;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionSettingList;
import nts.uk.ctx.exio.dom.exo.condset.CondSet;
import nts.uk.ctx.exio.dom.exo.condset.StandardAtr;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLog;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLogRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExecHistService {

	@Inject
	private AcquisitionExternalOutputCategory acquisitionExternalOutputCategory;

	@Inject
	private AcquisitionSettingList acquisitionSettingList;

	@Inject
	private PersonInfoAdapter personInfoAdapter;

	@Inject
	private ExterOutExecLogRepository exterOutExecLogRepo;

	/**
	 * 起動する
	 */
	public ExecHistResult initScreen(List<String> inChargeRole, List<String> empRole) {
		ExecHistResult result = new ExecHistResult();
		// アルゴリズム「外部出力条件設定一覧」を実行する
		this.getExOutCondSetList(result, inChargeRole, empRole);
		// アルゴリズム「外部出力実行履歴」を実行する
		this.getExOutExecHist(result, inChargeRole);
		return result;
	}

	/**
	 * 外部出力条件設定一覧
	 */
	public void getExOutCondSetList(ExecHistResult result, List<String> inChargeRole, List<String> empRole) {
		// CondSetAndCtg result = new CondSetAndCtg(new ArrayList<>(), new
		// ArrayList<>());
		String cid = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		List<CondSet> condSetList = new ArrayList<>();
		// 「担当ロール（リスト）」
		if (inChargeRole.isEmpty()) {
			// アルゴリズム「外部出力取得設定一覧」を実行する
			// pending
		} else {
			// アルゴリズム「外部出力カテゴリ取得リスト」を実行する
			result.setExOutCtgIdList(acquisitionExternalOutputCategory.getExternalOutputCategoryList(empRole));
			// ドメインモデル「出力条件設定（ユーザ）」を取得する
			// pending
			// アルゴリズム「外部出力取得設定一覧」を実行する
			condSetList = acquisitionSettingList.getAcquisitionSettingList(cid, employeeId, StandardAtr.STANDARD,
					Optional.empty());
		}

		// 条件設定の定型とユーザを合わせる
		condSetList.sort((o1, o2) -> o1.getStandardAtr().value - o2.getStandardAtr().value);
		condSetList.sort((o1, o2) -> o1.getConditionSetCode().v().compareTo(o2.getConditionSetCode().v()));
		result.setCondSetList(condSetList);
	}

	/**
	 * 外部出力実行履歴
	 * 
	 * @param exOutCtgList
	 *            外部出力カテゴリ（リスト）
	 */
	private void getExOutExecHist(ExecHistResult result, List<String> inChargeRole) {
		String userId = AppContexts.user().employeeId();
		// 初期値セット
		// List<String> condSetCdList = new ArrayList<>();
		result.setStartDate(GeneralDate.today().addMonths(-1).addDays(1));
		result.setEndDate(GeneralDate.today());
		// アルゴリズム「外部出力実行履歴検索」を実行する
		result.setExecHistList(this.getExOutExecHistSearch(result.getStartDate(), result.getEndDate(), userId,
				Optional.empty(), result.getExOutCtgIdList(), inChargeRole));
	}

	/**
	 * 外部出力実行履歴検索
	 */
	public List<ExecHist> getExOutExecHistSearch(GeneralDate startDate, GeneralDate endDate, String userId,
			Optional<String> condSetCd, List<Integer> exOutCtgIdList, List<String> inChargeRole) {
		String cid = AppContexts.user().companyId();
		List<ExterOutExecLog> exterOutExecLogList = new ArrayList<>();
		GeneralDateTime start = GeneralDateTime.ymdhms(startDate.year(), startDate.month(), startDate.day(), 0, 0, 0);
		GeneralDateTime end = GeneralDateTime.ymdhms(endDate.year(), endDate.month(), endDate.day(), 23, 59, 59);
		// 担当ロール（リスト）
		if (inChargeRole.isEmpty()) {
			// ドメインモデル「外部出力実行結果ログ」を取得する
			exterOutExecLogList = exterOutExecLogRepo.searchExterOutExecLogGeneral(cid, start, end, userId, condSetCd);
		} else {
			// ドメインモデル「外部出力実行結果ログ」を取得する
			exterOutExecLogList = exterOutExecLogRepo.searchExterOutExecLogInchage(cid, start, end, userId, condSetCd,
					exOutCtgIdList);
		}

		// アルゴリズム「社員IDから個人社員基本情報を取得」を実行する
		List<String> sIds = exterOutExecLogList.stream().map(hist -> {
			return hist.getExecuteId();
		}).collect(Collectors.toList());
		List<PersonInfoImport> personInfoList = personInfoAdapter.listPersonInfor(sIds);

		return ExecHist.fromExterOutExecLogAndPersonInfo(exterOutExecLogList, personInfoList);
	}
}
