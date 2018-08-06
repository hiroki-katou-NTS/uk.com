package nts.uk.ctx.workflow.dom.resultrecord;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface AppRootDynamicRepository {
	
	public Optional<AppRootDynamic> findByID(String rootID);
	
	public void insert(AppRootDynamic appRootDynamic);
	
	public void update(AppRootDynamic appRootDynamic);
	
	public void delete(AppRootDynamic appRootDynamic);
	
	public Optional<AppRootDynamic> findByEmpDate(String companyID, String employeeID, GeneralDate recordDate, RecordRootType rootType);
	
	public Optional<AppRootDynamic> findByEmpDateNewest(String companyID, String employeeID, RecordRootType rootType);
	
	public List<AppRootDynamic> findByEmpLstPeriod(List<String> employeeIDLst, DatePeriod period, RecordRootType rootType);
	
}
