package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime;

import java.util.List;
import java.util.Optional;

public interface MaxDayOfWorkTimeCompanyRepo {
	
	/**
	 * insert(会社の就業時間帯の期間内上限勤務)
	 * @param companyId
	 * @param domain
	 */
	public void insert(String companyId, MaxDayOfWorkTimeCompany domain);
	
	/**
	 * update(会社の就業時間帯の期間内上限勤務)
	 * @param companyId
	 * @param domain
	 */
	public void update(String companyId, MaxDayOfWorkTimeCompany domain);
	
	/**
	 * exists(会社ID, 就業時間帯上限コード)
	 * @param companyId
	 * @param code
	 * @return
	 */
	public boolean exists(String companyId, WorkTimeMaximumCode code);
	
	/**
	 * delete(会社ID, 就業時間帯上限コード)
	 * @param companyId
	 * @param code
	 */
	public void delete(String companyId, WorkTimeMaximumCode code);
	
	/**
	 * get
	 * @param companyId
	 * @param code
	 * @return
	 */
	public Optional<MaxDayOfWorkTimeCompany> get(String companyId, WorkTimeMaximumCode code);
	
	/**
	 * *get
	 * @param companyId
	 * @return
	 */
	public List<MaxDayOfWorkTimeCompany> getAll(String companyId);

}
