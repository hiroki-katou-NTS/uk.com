package nts.uk.ctx.exio.dom.exo.exechist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exo.adapter.bs.employee.PersonInfoAdapter;
import nts.uk.ctx.exio.dom.exo.adapter.bs.employee.PersonInfoImport;
import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleAtrImport;
import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleExportRepoAdapter;
import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleImport;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExternalOutputCategory;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionSettingList;
import nts.uk.ctx.exio.dom.exo.condset.CondSet;
import nts.uk.ctx.exio.dom.exo.condset.StandardAttr;
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
	private RoleExportRepoAdapter roleExportRepoAdapter;

	@Inject
	private PersonInfoAdapter personInfoAdapter;

	@Inject
	private ExterOutExecLogRepository exterOutExecLogRepo;

	/**
	 * 起動する
	 */
	public ExecHistResult initScreen() {
		ExecHistResult result = new ExecHistResult();
		// アルゴリズム「外部出力条件設定一覧」を実行する
		this.getExOutCondSetList(result);
		// アルゴリズム「外部出力実行履歴」を実行する
		this.getExOutExecHist(result);
		return result;
	}

	/**
	 * 外部出力条件設定一覧
	 */
	public void getExOutCondSetList(ExecHistResult result) {
		// CondSetAndCtg result = new CondSetAndCtg(new ArrayList<>(), new
		// ArrayList<>());
		String cid = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		List<CondSet> condSetList = new ArrayList<>();
		// imported(補助機能)「ロール」を取得する Get "role"
		Optional<RoleImport> roleOtp = roleExportRepoAdapter.findByRoleId(this.getRoleId());
		if (!roleOtp.isPresent()) {
			return;
		}
		// ロール区分
		// 担当権限の場合
		if (RoleAtrImport.INCHARGE.equals(roleOtp.get().getAssignAtr())) {
			// アルゴリズム「外部出力カテゴリ取得リスト」を実行する
			// TODO
			List<String> roleIdList = new ArrayList<>();
			result.setExOutCtgIdList(acquisitionExternalOutputCategory.getExternalOutputCategoryList(roleIdList));
			// ドメインモデル「出力条件設定（ユーザ）」を取得する
			// TODO pending
			// アルゴリズム「外部出力取得設定一覧」を実行する
			condSetList = acquisitionSettingList.getAcquisitionSettingList(cid, employeeId, StandardAttr.STANDARD,
					Optional.empty());
		}
		// 一般権限の場合
		else if (RoleAtrImport.GENERAL.equals(roleOtp.get().getAssignAtr())) {
			// アルゴリズム「外部出力取得設定一覧」を実行する
			// TODO pending
			// condSetList =
			// acquisitionSettingList.getAcquisitionSettingList(cid, employeeId,
			// StandardAttr.USER, Optional.empty());
		}

		// 条件設定の定型とユーザを合わせる
		condSetList.sort((o1, o2) -> o1.getStandardAttr().value - o2.getStandardAttr().value);
		condSetList.sort((o1, o2) -> o1.getConditionSetCode().v().compareTo(o2.getConditionSetCode().v()));
		result.setCondSetList(condSetList);

		// 取得した一覧の先頭に「すべて」を追加
	}

	/**
	 * 外部出力実行履歴
	 * 
	 * @param exOutCtgList
	 *            外部出力カテゴリ（リスト）
	 */
	private void getExOutExecHist(ExecHistResult result) {
		String userId = AppContexts.user().employeeId();
		// 初期値セット
		// List<String> condSetCdList = new ArrayList<>();
		result.setStartDate(GeneralDate.today().addMonths(-1).addDays(1));
		result.setEndDate(GeneralDate.today());
		// アルゴリズム「外部出力実行履歴検索」を実行する
		result.setExecHistList(this.getExOutExecHistSearch(result.getStartDate(), result.getEndDate(), userId,
				Optional.empty(), result.getExOutCtgIdList()));
	}

	/**
	 * 外部出力実行履歴検索
	 */
	public List<ExecHist> getExOutExecHistSearch(GeneralDate startDate, GeneralDate endDate, String userId,
			Optional<String> condSetCd, List<Integer> exOutCtgIdList) {
		String cid = AppContexts.user().companyId();
		List<ExterOutExecLog> exterOutExecLogList = new ArrayList<>();
		Optional<RoleImport> roleOtp = roleExportRepoAdapter.findByRoleId(this.getRoleId());
		if (!roleOtp.isPresent()) {
			return Collections.emptyList();
		}
		GeneralDateTime start = GeneralDateTime.ymdhms(startDate.year(), startDate.month(), startDate.day(), 0, 0, 0);
		GeneralDateTime end = GeneralDateTime.ymdhms(endDate.year(), endDate.month(), endDate.day(), 23, 59, 59);
		// ロール区分
		// 担当権限の場合
		if (RoleAtrImport.INCHARGE.equals(roleOtp.get().getAssignAtr())) {
			// ドメインモデル「外部出力実行結果ログ」および「出力条件設定」を取得する
			exterOutExecLogList = exterOutExecLogRepo.searchExterOutExecLogInchage(cid, start, end, userId, condSetCd,
					exOutCtgIdList);
		}
		// 一般権限の場合
		else if (RoleAtrImport.GENERAL.equals(roleOtp.get().getAssignAtr())) {
			exterOutExecLogList = exterOutExecLogRepo.searchExterOutExecLogGeneral(cid, start, end, userId, condSetCd);
		}

		// アルゴリズム「社員IDから個人社員基本情報を取得」を実行する
		List<String> sIds = exterOutExecLogList.stream().map(hist -> {
			return hist.getExecuteId();
		}).collect(Collectors.toList());
		List<PersonInfoImport> personInfoList = personInfoAdapter.listPersonInfor(sIds);

		return ExecHist.fromExterOutExecLogAndPersonInfo(exterOutExecLogList, personInfoList);
	}

	private String getRoleId() {
		// TODO roleId
		String roleId = AppContexts.user().roles().forAttendance();
		return roleId;
	}
}
