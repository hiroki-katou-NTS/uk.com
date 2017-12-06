package nts.uk.ctx.at.auth.pub.wkpmanager;

import java.util.List;

public interface WorkplaceManagerPub {
	// アルゴリズム「職場管理者登録基本データ取得」を実行する
	List<WorkplaceManagerExport> getAllWorkplaceManager();
}
