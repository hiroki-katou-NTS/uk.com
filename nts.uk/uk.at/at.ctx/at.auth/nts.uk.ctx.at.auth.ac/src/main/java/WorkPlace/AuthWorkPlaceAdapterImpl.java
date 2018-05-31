package WorkPlace;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.adapter.AuthWorkPlaceAdapter;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;

@Stateless
public class AuthWorkPlaceAdapterImpl implements AuthWorkPlaceAdapter{
	
	@Inject
	private EmployeePublisher employeePublisher;

	@Override
	public List<String> getListWorkPlaceID(String employeeID, GeneralDate referenceDate) {
		List<String> listWorkPlaceID = employeePublisher.getListWorkPlaceID(employeeID, referenceDate);
		return listWorkPlaceID;
	}

}
