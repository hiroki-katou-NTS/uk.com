package nts.uk.ctx.at.auth.pubimp.wkpmanager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nts.uk.ctx.at.auth.dom.wkpmanager.WorkplaceManagerRepository;
import nts.uk.ctx.at.auth.pub.wkpmanager.WorkplaceManagerExport;
import nts.uk.ctx.at.auth.pub.wkpmanager.WorkplaceManagerPub;

public class WorkplaceManagerPubImpl implements WorkplaceManagerPub {
	@Inject
	private WorkplaceManagerRepository repo;
	
	/**
	 * アルゴリズム「職場管理者登録基本データ取得」を実行する
	 */
	@Override
	public List<WorkplaceManagerExport> getAllWorkplaceManager() {
		List<WorkplaceManagerExport> wkpManagerList = new ArrayList<WorkplaceManagerExport>();
		/*
		 *  アルゴリズム「職場情報の取得（起動時用）」を実行する
		 *  output：
		 * ・職場ID
		 * ・職場表示名
		 */
		List<String> workplaceList = repo.getAllWorkplaceList();
		/*
		 *  アルゴリズム「職場管理者情報の取得」を実行する
		 *  input：
		 *  ・会社ID
		 *  ・職場ID
		 *  ・基準日=システム日付
		 *  output：
		 *  ・職場ID
		 *  ・社員ID
		 *  ・期間
		 *  ・ロールID
		 */
		for (String workplace : workplaceList) {
			repo.getAllWorkplaceManagerList("", "", null);
		}
		
		return wkpManagerList;
	}

}
