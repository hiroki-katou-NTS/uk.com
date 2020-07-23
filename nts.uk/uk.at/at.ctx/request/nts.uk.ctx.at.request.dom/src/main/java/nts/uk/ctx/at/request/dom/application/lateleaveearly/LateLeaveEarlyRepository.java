package nts.uk.ctx.at.request.dom.application.lateleaveearly;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoOutput;

/**
 * @author anhnm
 *
 */
public interface LateLeaveEarlyRepository {

	/**
	 * 遅刻早退取消申請の新規登録
	 *
	 * @param appType
	 * @param infoOutput
	 * @param application
	 */
	void register(int appType, ArrivedLateLeaveEarlyInfoOutput infoOutput, Application application);
}
