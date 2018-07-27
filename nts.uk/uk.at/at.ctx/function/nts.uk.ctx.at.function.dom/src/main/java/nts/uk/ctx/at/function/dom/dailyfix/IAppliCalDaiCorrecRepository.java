package nts.uk.ctx.at.function.dom.dailyfix;

import java.util.List;

public interface IAppliCalDaiCorrecRepository {
	/**
	 * find list AppliCalDaiCorrec by companyId
	 * @param companyId
	 * @return
	 * @author yennth
	 */
	List<ApplicationCall> findByCom(String companyId);
	/**
	 * update AppliCalDaiCorrec
	 * @param appliCalDaiCorrec
	 * @author yennth
	 */
//	void update(AppliCalDaiCorrec appliCalDaiCorrec);
	/**
	 * delete list AppliCalDaiCorrec
	 * @param companyId
	 * @author yennth
	 */
	void delete(String companyId);
	/**
	 * insert AppliCalDaiCorrec
	 * @param appliCalDaiCorrec
	 * @author yennth
	 */
	void insert(ApplicationCall appliCalDaiCorrec);
}
