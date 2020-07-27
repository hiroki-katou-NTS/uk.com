package nts.uk.ctx.at.request.dom.application.lateleaveearly;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateEarlyDateChangeOutput;

/**
 * @author anhnm
 *
 */
public interface LateLeaveEarlyService {

	/**
	 * 起動する getLateLeaveEarlyInfo
	 *
	 * @return ArrivedLateLeaveEarlyInfoOutput the lateEarlyLeaveInfo
	 */
	ArrivedLateLeaveEarlyInfoOutput getLateLeaveEarlyInfo(int appId, List<String> appDates);

	/**
	 * 申請日を変更する
	 *
	 * @param appType
	 * @param appDate
	 * @return
	 */
	LateEarlyDateChangeOutput getChangeAppDate(int appType, List<String> appDates, String appDate,
			AppDispInfoNoDateOutput appDispNoDate, AppDispInfoWithDateOutput appDispWithDate);

	/**
	 * 共通登録前のエラーチェック処理
	 *
	 * @param appType
	 * @param appDates
	 * @param appDate
	 * @param appDispNoDate
	 * @param appDispWithDate
	 * @return
	 */
	List<String> getMessageList(int appType, boolean agentAtr, boolean isNew,
			ArrivedLateLeaveEarlyInfoOutput infoOutput, Application application);

	/**
	 * 遅刻早退取消申請の新規登録
	 *
	 * @param appType
	 * @param infoOutput
	 * @param application
	 */
	void register(int appType, ArrivedLateLeaveEarlyInfoOutput infoOutput, Application application);
}
