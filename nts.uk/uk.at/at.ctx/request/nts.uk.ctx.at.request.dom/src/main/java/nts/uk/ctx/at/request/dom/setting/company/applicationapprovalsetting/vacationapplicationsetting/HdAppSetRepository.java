package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting;

import java.util.List;
import java.util.Optional;

public interface HdAppSetRepository {
	/**
	 * get all hd app set by companyId
	 * @return
	 * @author yennth
	 */
	List<HdAppSet> getAll();
	/**
	 * get hd app set by hd app type
	 * @param hdAppType
	 * @return
	 * @author yennth
	 */
	Optional<HdAppSet> getHdAppSet(int hdAppType);
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
