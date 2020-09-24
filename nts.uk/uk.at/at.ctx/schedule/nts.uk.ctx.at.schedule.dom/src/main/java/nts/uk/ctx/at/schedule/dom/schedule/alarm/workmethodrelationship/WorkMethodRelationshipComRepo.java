package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import java.util.List;
import java.util.Optional;

public interface WorkMethodRelationshipComRepo {
	
	public void insert(WorkMethodRelationshipCom domain);
	
	public void update(WorkMethodRelationshipCom domain);
	
	/**
	 * delete ( 会社ID )	
	 * @param companyId
	 */
	public void deleteAll(String companyId);
	
	/**
	 * delete ( 会社ID, 勤務方法 )
	 * @param companyId
	 * @param workMethod
	 */
	public void deleteWorkMethod(String companyId, WorkMethod workMethod);
	
	/**
	 * getAll ( 会社ID )
	 * @param companyId
	 * @return
	 */
	public List<WorkMethodRelationshipCom> getAll(String companyId);
	
	/**
	 * get ( 会社ID, 勤務方法 )
	 * @param companyId
	 * @param workMethod
	 * @return
	 */
	public Optional<WorkMethodRelationshipCom> getWithWorkMethod(String companyId, WorkMethod workMethod);
	
	/**
	 * get ( 会社ID, List<勤務方法> )	
	 * @param companyId
	 * @param workMethodList
	 * @return
	 */
	public List<WorkMethodRelationshipCom> getWithWorkMethodList(String companyId, List<WorkMethod> workMethodList);
	
	/**
	 * xists ( 会社ID, 勤務方法 )
	 * @param companyId
	 * @param workMethod
	 * @return
	 */
	public boolean exists(String companyId, WorkMethod workMethod);

}
