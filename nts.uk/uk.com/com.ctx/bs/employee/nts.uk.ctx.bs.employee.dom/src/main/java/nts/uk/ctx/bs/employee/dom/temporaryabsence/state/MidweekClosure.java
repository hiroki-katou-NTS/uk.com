/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence.state;

import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempLeaveAbsenceHisItem;

/**
 * @author danpv
 * Domain Name : 産前休業
 *
 */
public class MidweekClosure extends TempLeaveAbsenceHisItem {

	/**
	 * Type: Optional
	 * 多胎妊娠区分 Multiple pregnancy segment
	 */
	private Integer multiple;
	
	
	/**
	 * @param multiple
	 */
	public MidweekClosure(Integer multiple) {
		super();
		this.multiple = multiple;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	

}
