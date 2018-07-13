package nts.uk.ctx.exio.dom.exo.exechist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.adapter.bs.employee.PersonInfoAdapter;
import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleAtrImport;
import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleExportRepoAdapter;
import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleImport;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExternalOutputCategory;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionSettingList;
import nts.uk.ctx.exio.dom.exo.condset.CondSet;
import nts.uk.ctx.exio.dom.exo.condset.StandardAttr;
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

	/**
	 * 外部出力条件設定一覧
	 */
	public CondSetAndCtg getExOutCondSetList() {
		CondSetAndCtg result = new CondSetAndCtg(new ArrayList<>(), new ArrayList<>());
		String cid = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		// TODO roleId
		String roleId = AppContexts.user().roles().forAttendance();
		List<CondSet> condSetList = new ArrayList<>();
		// imported(補助機能)「ロール」を取得する Get "role"
		Optional<RoleImport> roleOtp = roleExportRepoAdapter.findByRoleId(roleId);
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
		else if (RoleAtrImport.GENERAL.equals(roleOtp.get().getAssignAtr())) {
			// アルゴリズム「外部出力取得設定一覧」を実行する
			condSetList = acquisitionSettingList.getAcquisitionSettingList(cid, employeeId, StandardAttr.USER,
					Optional.empty());
		}
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
	private void getExOutExecHist(List<ExOutCtg> exOutCtgList) {
		// 初期値セット
		// TODO
		// アルゴリズム「外部出力実行履歴検索」を実行する

	}

	/**
	 * 外部出力実行履歴検索
	 */
	public List<ExecHist> getExOutExecHistSearch() {
		// TODO roleId
		String roleId = AppContexts.user().roles().forAttendance();
		Optional<RoleImport> roleOtp = roleExportRepoAdapter.findByRoleId(roleId);
		if (!roleOtp.isPresent()) {
			return Collections.emptyList();
		}
		// ロール区分
		// 担当権限の場合
		if (RoleAtrImport.INCHARGE.equals(roleOtp.get().getAssignAtr())) {
			// ドメインモデル「外部出力実行結果ログ」および「出力条件設定」を取得する
			// TODO
		}
		// 一般権限の場合
		else if (RoleAtrImport.GENERAL.equals(roleOtp.get().getAssignAtr())) {
			// ドメインモデル「外部出力実行結果ログ」および「出力条件設定」を取得する
			// TODO
		}
		// アルゴリズム「社員IDから個人社員基本情報を取得」を実行する
		// personInfoAdapter.getPersonInfo(sID);
		return Collections.emptyList();
	}
}
