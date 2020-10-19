package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import java.util.List;
import java.util.Optional;

public interface WorkMethodRelationshipComRepo {
	
	public void insert(String companyId, WorkMethodRelationshipCompany domain);
	
	public void update(String companyId, WorkMethodRelationshipCompany domain);
	
	/**
	 * delete ( 会社ID )	
	 * @param companyId
	 */
	public void deleteAll(String companyId);
	
	/**
	 * delete ( 会社ID, 勤務方法 )
	 * @param companyId
	 * @param prevWorkMethod
	 */
	public void deleteWithWorkMethod(String companyId, WorkMethod prevWorkMethod);
	
	/**
	 * getAll ( 会社ID )
	 * @param companyId
	 * @return
	 */
	public List<WorkMethodRelationshipCompany> getAll(String companyId);
	
	/**
	 * get ( 会社ID, 勤務方法 )
	 * @param companyId
	 * @param prevWorkMethod
	 * @return
	 */
	public Optional<WorkMethodRelationshipCompany> getWithWorkMethod(String companyId, WorkMethod prevWorkMethod);
	
	/**
	 * get ( 会社ID, List<勤務方法> )	
	 * @param companyId
	 * @param prevWorkMethodList
	 * @return
	 */
	public List<WorkMethodRelationshipCompany> getWithWorkMethodList(String companyId, List<WorkMethod> prevWorkMethodList);
	
	/**
	 * xists ( 会社ID, 勤務方法 )
	 * @param companyId
	 * @param prevWorkMethod
	 * @return
	 */
	public boolean exists(String companyId, WorkMethod prevWorkMethod);

}
