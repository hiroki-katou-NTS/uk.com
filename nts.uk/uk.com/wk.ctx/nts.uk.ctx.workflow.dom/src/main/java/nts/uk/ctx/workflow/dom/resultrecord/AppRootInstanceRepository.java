package nts.uk.ctx.workflow.dom.resultrecord;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface AppRootInstanceRepository {
	
	public Optional<AppRootInstance> findByID(String rootID);
	
	public void insert(AppRootInstance appRootInstance);
	
	public void update(AppRootInstance appRootInstance);
	
	public void delete(AppRootInstance appRootInstance);
	
	/**
	 * 承認状態をクリアする
	 * @param appRootInstance
	 */
	public void clearStatus(String companyID, String employeeID, GeneralDate date, RecordRootType rootType);
	
	/**
	 * 承認状態を作成する
	 * @param appRootInstance
	 */
	public void createNewStatus(String companyID, String employeeID, GeneralDate date, RecordRootType rootType);
	
}
