/**
 * 
 */
package nts.uk.ctx.bs.employee.infra.repository.leaveholiday;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.leaveholiday.LeaveHoliday;
import nts.uk.ctx.bs.employee.dom.leaveholiday.LeaveHolidayRepository;

/**
 * @author danpv
 *
 */
@Stateless
public class LeaveHolidayRepoImpl implements LeaveHolidayRepository{

	@Override
	public Optional<LeaveHoliday> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
