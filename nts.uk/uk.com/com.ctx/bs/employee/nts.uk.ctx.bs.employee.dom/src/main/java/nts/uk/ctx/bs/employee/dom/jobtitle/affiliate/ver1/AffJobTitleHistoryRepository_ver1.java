package nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;

public interface AffJobTitleHistoryRepository_ver1 {
	
	/**
	 * get with primary key
	 * @param historyId
	 * @return
	 */
	Optional<AffJobTitleHistory_ver1> getByHistoryId(String historyId);
	
	/**
	 * get with employeeId and standard date
	 * @param employeeId
	 * @param standardDate
	 * @return
	 */
	Optional<AffJobTitleHistory_ver1> getByEmpIdAndStandardDate(String employeeId, GeneralDate standardDate);
	
	// get listbypid
	Optional<AffJobTitleHistory_ver1> getListBySid(String cid, String sid);
	
	Optional<AffJobTitleHistory_ver1> getListBySidDesc(String cid, String sid);

	/**
	 * ドメインモッ�「�務�位」を新規登録する
	 * 
	 * @param item
	 * @param sid
	 * @param cid
	 */
	void add(String cid, String sid, DateHistoryItem item);

	/**
	 * 取得した「�務�位」を更新する
	 * 
	 * @param item
	 */
	void update(DateHistoryItem item);

	/**
	 * ドメインモッ�「�務�位」を削除する
	 * 
	 * @param histId
	 */
	void delete(String histId);
	
	List<AffJobTitleHistory_ver1> getAllBySid(String sid);
	
	Optional<AffJobTitleHistory_ver1> getListByHidSid(String hid, String sid);
	
	/**
	 * Search job title history.
	 *
	 * @param baseDate the base date
	 * @param employeeId the employee id
	 * @return the list
	 */
	List<AffJobTitleHistory_ver1> searchJobTitleHistory(GeneralDate baseDate, List<String> employeeId);
	
	 /**
 	 * Find all job title history.
 	 *
 	 * @param baseDate the base date
 	 * @param employeeIds the employee ids
 	 * @return the list
 	 */
 	List<AffJobTitleHistory_ver1> findAllJobTitleHistory(GeneralDate baseDate, List<String> employeeIds) ;
}
