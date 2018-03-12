package nts.uk.ctx.at.request.dom.setting.company.displayname;

import java.util.List;
import java.util.Optional;
/**
 * Hd App Disp Name Repository interface
 * @author yennth
 *
 */
public interface HdAppDispNameRepository {
	/**
	 * get Holiday app disp name by companyId
	 * @return
	 * @author yennth
	 */
	List<HdAppDispName> getAllHdApp();
	/**
	 * get Holiday app disp name by companyId and hdAppType
	 * @param hdAppType
	 * @return
	 * @author yennth
	 */
	Optional<HdAppDispName> getHdApp(int hdAppType);
	/**
	 * update Hd App Disp Name
	 * @param appDisp
	 */
	void update(HdAppDispName appDisp);
	/**
	 * insert hd app disp name
	 * @param appDisp
	 * @author yennth
	 */
	void insert(HdAppDispName appDisp);
}
