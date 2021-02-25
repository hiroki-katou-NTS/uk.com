package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival;

import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflect;

/**
 * @author anhnm
 *
 */
public interface LateEarlyCancelAppSetRepository {

	LateEarlyCancelAppSet getByCId(String companyId);

	void save(String companyId, LateEarlyCancelAppSet setting, LateEarlyCancelReflect reflect);

}
