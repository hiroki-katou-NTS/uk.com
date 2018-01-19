package nts.uk.ctx.at.request.dom.setting.request.application;

import java.util.List;
import java.util.Optional;

public interface ApplicationDeadlineRepository {
	/**
	 * get deadline setting by closureId
	 * @param companyId
	 * @param closureId 締めＩＤ
	 * @return
	 */
	Optional<ApplicationDeadline> getDeadlineByClosureId(String companyId, int closureId);
	/**
	 * get deadline setting by app type
	 * @param companyId
	 * @param appType
	 * @return
	 */
	List<ApplicationDeadline> getDeadlineByAppType(String companyId, int appType);
	/**
	 * update by closureId
	 * @param companyId
	 * @param closureId
	 * @author yennth
	 */
	public void update(ApplicationDeadline appDeadline);
	/**
	 * insert by closureId
	 * @param companyId
	 * @param closureId
	 * @author yennth
	 */
	public void insert(ApplicationDeadline appDeadline);
}
