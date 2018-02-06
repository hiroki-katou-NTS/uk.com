package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting;

import java.util.Optional;

public interface HdAppSetRepository {
	/**
	 * get all hd app set by companyId
	 * @return
	 * @author yennth
	 */
	Optional<HdAppSet> getAll();
	/**
	 * update hd app set
	 * @param hdAppSet
	 * @author yennth
	 */
	void update(HdAppSet hdAppSet);
	/**
	 * insert hd app set
	 * @param hdAppSet
	 * @author yennth
	 */
	void insert(HdAppSet hdAppSet);
}
