package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import java.util.Optional;

/**
 * 並び替え設定Repository
 * @author Hieult
 *
 */
public interface SortSettingRepository {
	
	/** [1] Insert(並び替え設定)  //Insert(sort setting) **/
	void insert(SortSetting SortSetting);
	/** [2] Update(並び替え設定) //Update(sort setting) **/
	void update(SortSetting SortSetting);
	/** [3] Delete(会社ID) //Delete(companyID) **/
	void delete (String companyID);
	/** [4] get**/
	Optional<SortSetting> get(String companyID); 
}
