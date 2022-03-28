package nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee;

import java.util.List;
import java.util.Optional;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 応援可能な社員Repository
 * @author kumiko_otake
 *
 */
public interface SupportableEmployeeRepository {

	/**
	 * insert
	 * @param cid 会社ID
	 * @param domain 応援可能な社員
	 */
	public void insert(String cid, SupportableEmployee domain);
	/**
	 * update
	 * @param cid 会社ID
	 * @param domain 応援可能な社員
	 */
	public void update(String cid, SupportableEmployee domain);
	/**
	 * delete
	 * @param id 応援可能な社員ID
	 */
	public void delete(String id);

	/**
	 * get
	 * @param id 応援可能な社員ID
	 * @return 応援可能な社員
	 */
	public Optional<SupportableEmployee> get(String id);
	/**
	 * *get
	 * @param ids 応援可能な社員ID(List)
	 * @return 応援可能な社員(List)
	 */
	public List<SupportableEmployee> get(List<String> ids);

	/**
	 * exists
	 * @param id 応援可能な社員ID
	 * @return 存在するかどうか(true: 存在する / false: 存在しない)
	 */
	public boolean exists(String id);

	/**
	 * 社員を指定して取得する
	 * @param employeeId 社員ID
	 * @return 応援可能な社員(List)
	 */
	public List<SupportableEmployee> findByEmployeeId(EmployeeId employeeId);
	/**
	 * 社員を指定して取得する
	 * @param employeeIds 社員ID(List)
	 * @return 応援可能な社員(List)
	 */
	public List<SupportableEmployee> findByEmployeeId(List<EmployeeId> employeeIds);

	/**
	 * 社員と期間を指定して取得する
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 応援可能な社員(List)
	 */
	public List<SupportableEmployee> findByEmployeeIdWithPeriod(EmployeeId employeeId, DatePeriod period);
	/**
	 * 社員と期間を指定して取得する
	 * @param employeeId 社員ID(List)
	 * @param period 期間
	 * @return 応援可能な社員(List)
	 */
	public List<SupportableEmployee> findByEmployeeIdWithPeriod(List<EmployeeId> employeeIds, DatePeriod period);

	/**
	 * 応援先と期間を指定して取得する
	 * @param recipient 応援先組織
	 * @param period 期間
	 * @return 応援可能な社員(List)
	 */
	public List<SupportableEmployee> findByRecipientWithPeriod(TargetOrgIdenInfor recipient, DatePeriod period);

}
