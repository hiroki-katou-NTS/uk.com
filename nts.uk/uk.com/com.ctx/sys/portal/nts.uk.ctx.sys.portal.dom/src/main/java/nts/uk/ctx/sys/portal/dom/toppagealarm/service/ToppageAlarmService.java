package nts.uk.ctx.sys.portal.dom.toppagealarm.service;

import java.util.Optional;

import nts.uk.ctx.sys.portal.dom.toppagealarm.AlarmClassification;
import nts.uk.ctx.sys.portal.dom.toppagealarm.DisplayAtr;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmData;

/**
 * トップページアラームデータが存在するかのチェック
 *
 */
public interface ToppageAlarmService {

	/**
	 * [1] 存在するかのチェック
	 * @param require
	 * @param companyId
	 * @param alarmCls
	 * @param idenKey
	 * @param sId
	 * @param dispAtr
	 * @return
	 */
	public ToppageCheckResult isAlarmExisted(Require require, String companyId, AlarmClassification alarmCls, String idenKey, String sId, DisplayAtr dispAtr);

	public static interface Require {

		/**
		 * RP_トップページアラームデータRepository.取得する(会社ID, 表示社員ID, 識別キー, 表示社員区分)
		 */
		Optional<ToppageAlarmData> getOne(String companyId, AlarmClassification alarmCls, String idenKey, String sId, DisplayAtr dispAtr);
	}
	
}
