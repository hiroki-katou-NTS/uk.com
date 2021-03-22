package nts.uk.ctx.sys.portal.pub.toppagealarm;

import nts.arc.task.tran.AtomTask;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページアラーム（ver4～）.Export.トップページアラームデータを作成する.トップページアラームデータを作成する
 */
public interface ToppageAlarmPub {

	/**
	 * 作成する( 会社ID, トップアラームパラメータ, 削除の情報)
	 * 
	 * @param 会社ID         cid
	 * @param トップアラームパラメータ alarmInfo
	 * @param 削除の情報        delInfo
	 * @return AtomTask
	 */
	public AtomTask create(String cid, ToppageAlarmExport alarmInfo, DeleteInfoExport delInfo);
}
