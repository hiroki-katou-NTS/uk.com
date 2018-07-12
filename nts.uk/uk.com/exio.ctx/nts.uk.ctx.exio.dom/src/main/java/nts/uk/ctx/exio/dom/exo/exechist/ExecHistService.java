package nts.uk.ctx.exio.dom.exo.exechist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleAtr;
import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleExportRepoAdapter;
import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleImport;
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
		if (RoleAtr.INCHARGE.equals(roleOtp.get().getAssignAtr())) {
			// アルゴリズム「外部出力カテゴリ取得リスト」を実行する
			result.setExOutCtgList(acquisitionExternalOutputCategory.getExternalOutputCategoryList());
			// ドメインモデル「出力条件設定（ユーザ）」を取得する
			// TODO
			// アルゴリズム「外部出力取得設定一覧」を実行する
			condSetList = acquisitionSettingList.getAcquisitionSettingList(cid, employeeId, StandardAttr.STANDARD,
					Optional.empty());
		}
		// 一般権限の場合
		else if (RoleAtr.GENERAL.equals(roleOtp.get().getAssignAtr())) {
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
	 */
	public void getExOutExecHist() {

	}
}
