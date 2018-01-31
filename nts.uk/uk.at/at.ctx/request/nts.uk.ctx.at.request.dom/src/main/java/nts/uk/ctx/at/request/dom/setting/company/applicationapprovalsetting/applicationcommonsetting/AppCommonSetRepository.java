package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting;

import java.util.Optional;
/**
 * the app common set interface
 * @author yennth
 *
 */
public interface AppCommonSetRepository {
	/**
	 * find app common set by companyId
	 * @return
	 */
	Optional<AppCommonSet> find();
	/**
	 * update app common set
	 */
	void update(AppCommonSet appCommon);
	/**
	 * insert app common set
	 */
	void insert(AppCommonSet appCommon);
}
