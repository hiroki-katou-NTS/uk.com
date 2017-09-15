package nts.uk.ctx.bs.employee.pub.employee.employeeInfo.personinfo;

/**
 * 
 * Interface Person information
 *
 */
public interface PersonInforPub {

	/**
	 * Get Informantion of Person by For request No.01
	 */
	PersonInfoDtoExport getPersonInfomation(String employeeId);

}
