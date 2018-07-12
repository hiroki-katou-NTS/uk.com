package nts.uk.ctx.exio.dom.exo.exechist;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionExternalOutputCategory;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionSettingList;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExecHistService {

	@Inject
	private AcquisitionExternalOutputCategory acquisitionExternalOutputCategory;

	@Inject
	private AcquisitionSettingList acquisitionSettingList;

	/**
	 * 外部出力条件設定一覧
	 */
	public CondSetAndCtg getExOutCondSetList() {
		CondSetAndCtg result = new CondSetAndCtg(new ArrayList<>(), new ArrayList<>());
		String cid = AppContexts.user().companyId();
		List<StdOutputCondSet> stdOutCondList;
		List<CondSet> condSetList = new ArrayList<>();
		// imported(補助機能)「ロール」を取得する Get "role"
		// TODO
		// ロール区分
		// 担当権限の場合
		if (true) {
			// アルゴリズム「外部出力カテゴリ取得リスト」を実行する
			result.setExOutCtgList(acquisitionExternalOutputCategory.getExternalOutputCategoryList());
			// ドメインモデル「出力条件設定（ユーザ）」を取得する
			// TODO
			// アルゴリズム「外部出力取得設定一覧」を実行する
			stdOutCondList = acquisitionSettingList.getAcquisitionSettingList(cid, null);
		}
		// 一般権限の場合
		else {
			// アルゴリズム「外部出力取得設定一覧」を実行する
			stdOutCondList = acquisitionSettingList.getAcquisitionSettingList(cid, null);
		}
		// 条件設定の定型とユーザを合わせる
		for (StdOutputCondSet item : stdOutCondList) {

		}

		// 取得した一覧の先頭に「すべて」を追加
		// TODO
		return result;
	}

	/**
	 * 外部出力実行履歴
	 */
	public void getExOutExecHist() {

	}
}
