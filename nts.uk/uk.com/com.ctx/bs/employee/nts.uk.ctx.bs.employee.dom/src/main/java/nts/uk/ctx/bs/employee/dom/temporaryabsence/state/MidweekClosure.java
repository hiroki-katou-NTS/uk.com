/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence.state;

import nts.arc.time.GeneralDate;

/**
 * @author danpv
 *
 */
public class MidweekClosure extends LeaveHolidayState {

	/**
	 * 出産日 birth date
	 */
	private GeneralDate birthDate;

	/**
	 * 多胎妊娠区分 Multiple pregnancy segment
	 */
	private int multiple;
	
	
	/**
	 * @param birthDate
	 * @param multiple
	 */
	public MidweekClosure(GeneralDate birthDate, int multiple) {
		super();
		this.birthDate = birthDate;
		this.multiple = multiple;
	}

	public GeneralDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(GeneralDate birthDate) {
		this.birthDate = birthDate;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}
	

}
