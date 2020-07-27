package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival;

import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarly;

/**
 * @author anhnm
 *
 */
public interface LateEarlyCancelAppSetRepository {

	LateEarlyCancelAppSet getByCId(String companyId);

	ArrivedLateLeaveEarly getLateEarlyApp(String companyId, String appId);
}
