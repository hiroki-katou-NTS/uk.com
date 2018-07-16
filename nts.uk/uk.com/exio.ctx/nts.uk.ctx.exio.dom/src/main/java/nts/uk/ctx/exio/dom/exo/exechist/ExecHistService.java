package nts.uk.ctx.exio.dom.exo.exechist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exo.adapter.bs.employee.PersonInfoAdapter;
import nts.uk.ctx.exio.dom.exo.adapter.bs.employee.PersonInfoImport;
import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleAtrImport;
import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleExportRepoAdapter;
import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleImport;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;
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
	public CondSetAndExecHist initScreen() {
		// アルゴリズム「外部出力条件設定一覧」を実行する
		CondSetAndCtg conSetAndCtg = this.getExOutCondSetList();
		// アルゴリズム「外部出力実行履歴」を実行する
		return new CondSetAndExecHist(this.getExOutExecHist(conSetAndCtg.getExOutCtgList()),
				conSetAndCtg.getCondSetList());
	}

	/**
	 * 外部出力条件設定一覧
	 */
	public CondSetAndCtg getExOutCondSetList() {
		CondSetAndCtg result = new CondSetAndCtg(new ArrayList<>(), new ArrayList<>());
		String cid = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		List<CondSet> condSetList = new ArrayList<>();
		// imported(補助機能)「ロール」を取得する Get "role"
		Optional<RoleImport> roleOtp = roleExportRepoAdapter.findByRoleId(this.getRoleId());
		if (!roleOtp.isPresent()) {
			return result;
		}
		// ロール区分
		// 担当権限の場合
		if (RoleAtrImport.INCHARGE.equals(roleOtp.get().getAssignAtr())) {
			// アルゴリズム「外部出力カテゴリ取得リスト」を実行する
			result.setExOutCtgList(acquisitionExternalOutputCategory.getExternalOutputCategoryList());
			// ドメインモデル「出力条件設定（ユーザ）」を取得する
			// TODO
			// アルゴリズム「外部出力取得設定一覧」を実行する
			condSetList = acquisitionSettingList.getAcquisitionSettingList(cid, employeeId, StandardAttr.STANDARD,
					Optional.empty());
		}
		// 一般権限の場合
		/*
		 * else if (RoleAtrImport.GENERAL.equals(roleOtp.get().getAssignAtr()))
		 * { // アルゴリズム「外部出力取得設定一覧」を実行する condSetList =
		 * acquisitionSettingList.getAcquisitionSettingList(cid, employeeId,
		 * StandardAttr.USER, Optional.empty()); }
		 */
		// 条件設定の定型とユーザを合わせる
		condSetList.sort((o1, o2) -> o1.getStandardAttr().value - o2.getStandardAttr().value);
		condSetList.sort((o1, o2) -> o1.getConditionSetCode().v().compareTo(o2.getConditionSetCode().v()));
		result.setCondSetList(condSetList);

		// 取得した一覧の先頭に「すべて」を追加
		return result;
	}

	/**
	 * 外部出力実行履歴
	 * 
	 * @param exOutCtgList
	 *            外部出力カテゴリ（リスト）
	 */
	private List<ExecHist> getExOutExecHist(List<ExOutCtg> exOutCtgList) {
		String userId = AppContexts.user().employeeId();
		// 初期値セット
		List<CondSet> condSetList = new ArrayList<>();
		GeneralDate delStartDate = GeneralDate.today().addMonths(-1).addDays(1);
		GeneralDate delEndDate = GeneralDate.today();
		// アルゴリズム「外部出力実行履歴検索」を実行する
		return this.getExOutExecHistSearch(delStartDate, delEndDate, userId, Optional.empty(), exOutCtgList,
				condSetList);
	}

	/**
	 * 外部出力実行履歴検索
	 */
	public List<ExecHist> getExOutExecHistSearch(GeneralDate startDate, GeneralDate endDate, String userId,
			Optional<String> condSetCd, List<ExOutCtg> exOutCtgList, List<CondSet> condSetList) {
		String cid = AppContexts.user().companyId();
		List<ExterOutExecLog> exterOutExecLogList = new ArrayList<>();
		Optional<RoleImport> roleOtp = roleExportRepoAdapter.findByRoleId(this.getRoleId());
		if (!roleOtp.isPresent()) {
			return Collections.emptyList();
		}
		// ロール区分
		// 担当権限の場合
		if (RoleAtrImport.INCHARGE.equals(roleOtp.get().getAssignAtr())) {
			// ドメインモデル「外部出力実行結果ログ」および「出力条件設定」を取得する
			// TODO
			exterOutExecLogList = exterOutExecLogRepo.searchExterOutExecLog(cid, startDate, endDate, userId, condSetCd);
		}
		// 一般権限の場合
		/*
		 * else if (RoleAtrImport.GENERAL.equals(roleOtp.get().getAssignAtr()))
		 * { }
		 */
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
