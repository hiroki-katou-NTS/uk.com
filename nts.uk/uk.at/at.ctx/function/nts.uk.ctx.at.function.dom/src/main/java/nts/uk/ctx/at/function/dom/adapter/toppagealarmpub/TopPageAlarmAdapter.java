package nts.uk.ctx.at.function.dom.adapter.toppagealarmpub;

import java.util.List;
import java.util.Optional;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.Import.トップページアラームデータを作成する
 * トップページアラームデータを作成するAdapter
 *
 */
public interface TopPageAlarmAdapter {
	/**
	 * Top page create alarm
	 * 
	 * @param companyId 会社ID
	 * @param alarmInfos List トップアラームパラメータ
	 * @param delInfo 削除の情報
	 */
	void create(String companyId, List<TopPageAlarmImport> alarmInfos, Optional<DeleteInfoAlarmImport> delInfoOpt);
}
