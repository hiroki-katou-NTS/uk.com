package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.refersToInformation;

import java.util.List;
import java.util.Optional;

/**
 * 	社員の静脈情報Repository
 * @author chungnt
 *
 */

public interface EmployeeVeinInformationRepository {
	
	/**
	 * 	[1] insert(打刻入力の共通設定)
	 */
	public void insert(EmployeeVeinInformation domain);

	/**
	 *  [2] update(打刻入力の共通設定)
	 * 
	 * @param 打刻機能の利用設定
	 *            domain
	 */
	public void update(EmployeeVeinInformation domain);

	/**
	 * 	[3] delete(社員の静脈情報)
	 * 
	 * @param 会社ID
	 *            comppanyID
	 * 
	 */
	public void delete(EmployeeVeinInformation domain);
	
	/**
	 * 	[4] get
	 * @param 	社員ID
	 * 			employeeId
	 * @return 	Optional<社員の静脈情報>
	 */
	public Optional<EmployeeVeinInformation> get(String employeeId);
	
	/**
	 * 	[5] get
	 * @param 	List<社員ID>
	 * 			List<employeeId>
	 * @return 	Optional<社員の静脈情報>
	 */
	public List<EmployeeVeinInformation> get(List<String> employeeIds);
}
