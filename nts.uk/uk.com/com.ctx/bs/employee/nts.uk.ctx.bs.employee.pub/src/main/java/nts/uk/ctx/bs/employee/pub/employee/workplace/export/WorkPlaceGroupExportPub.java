package nts.uk.ctx.bs.employee.pub.employee.workplace.export;

import java.util.List;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author HieuLt
 *
 */
public interface WorkPlaceGroupExportPub {
	
	
	/**
	 * 参照可能な所属社員を取得する
	 * @param referenceDate ---基準日
	 * @param employeeId ---社員ID
	 * @param workplaceGroupId --- 職場グループID
	 * @return List<社員ID>
	 * 
	 */
	public List<String> getReferenceableEmployees(GeneralDate referenceDate,String employeeId, String workplaceGroupId );
	
	/**
	 * 所属する社員をすべて取得する
	 * @param referenceDate
	 * @param workplaceGroupId
	 * @return List<社員の所属組織>
	 */
	public List<EmployeeOrganizationExport> getAllEmp(GeneralDate referenceDate, String workplaceGroupId );
	/**
	 * 所属する職場をすべて取得する
	 * @param referenceDate --- 基準日
	 * @param workplaceGroupId --- 職場グループID
	 * @return int
	 */
	public int getAllWorkplace(GeneralDate referenceDate, String workplaceGroupId);
	
	public List<WorkplaceGroupExport> getSpecifyingWorkplaceGroupId( List<String> workplacegroupId);
 	
}
