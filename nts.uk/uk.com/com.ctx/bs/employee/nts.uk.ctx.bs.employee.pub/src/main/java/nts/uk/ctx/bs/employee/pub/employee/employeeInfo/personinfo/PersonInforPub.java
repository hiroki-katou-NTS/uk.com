package nts.uk.ctx.bs.employee.pub.employee.employeeInfo.personinfo;

import java.util.List;

/**
 * 
 * Interface Person information
 *
 */
public interface PersonInforPub {

	/**
	 * Get List Employee by For request No.01
	 */
	PersonInfoDtoExport getPersonInfomation(String employeeId);

}
