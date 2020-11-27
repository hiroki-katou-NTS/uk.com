package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import java.util.Optional;

public interface ScheAuthModifyDeadlineRepository {
	
	/**
	 * Insert (スケジュール修正の修正期限)
	 * @param modifyDeadline
	 */
	void insert (String companyId, ScheAuthModifyDeadline modifyDeadline);
	
	/**
	 * Update (スケジュール修正の修正期限)
	 * @param modifyDeadline
	 */
	void update (String companyId, ScheAuthModifyDeadline modifyDeadline);
	
	/**
	 * Delete (会社ID, ロールID)
	 * @param companyId
	 * @param roleId
	 */
	void delete (String companyId, String roleId);
	
	/**
	 * get
	 * @param companyId
	 * @param roleId
	 * @return
	 */
	Optional<ScheAuthModifyDeadline> get (String companyId, String roleId);
}
