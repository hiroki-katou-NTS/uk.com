package nts.uk.ctx.at.request.dom.application.lateleaveearly;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateEarlyDateChangeOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSet;

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
	ArrivedLateLeaveEarlyInfoOutput getLateLeaveEarlyInfo(int appId, List<String> appDates,
			AppDispInfoStartupOutput appDispInfoStartupOutput);

	/**
	 * 申請日を変更する
	 *
	 * @param appType
	 * @param appDate
	 * @return
	 */
	LateEarlyDateChangeOutput getChangeAppDate(int appType, List<String> appDates, String appDate,
			AppDispInfoNoDateOutput appDispNoDate, AppDispInfoWithDateOutput appDispWithDate,
			LateEarlyCancelAppSet setting);

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
	ProcessResult register(int appType, ArrivedLateLeaveEarlyInfoOutput infoOutput, Application application);

	/**
	 * 起動する
	 *
	 * @param appId
	 * @return
	 */
	ArrivedLateLeaveEarlyInfoOutput getInitB(String appId, AppDispInfoStartupOutput infoStartupOutput);

	/**
	 * 遅刻早退報告申請の登録更新
	 *
	 * @param companyId
	 * @param application
	 * @param arrivedLateLeaveEarly
	 * @return
	 */
	ProcessResult update(String companyId, Application application, ArrivedLateLeaveEarly arrivedLateLeaveEarly, AppDispInfoStartupOutput infoStartupOutput);
}
