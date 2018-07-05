package WorkPlace;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.adapter.AuthWorkPlaceAdapter;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;
import nts.uk.ctx.sys.auth.pub.workplace.WorkplaceListPub;


@Stateless
public class AuthWorkPlaceAdapterImpl implements AuthWorkPlaceAdapter{
	
	@Inject
	private EmployeePublisher employeePublisher;

	@Inject
	private WorkplaceListPub  workplaceListPub ;
	
	@Override
	public List<String> getListWorkPlaceID(String employeeID, GeneralDate referenceDate) {
		List<String> listWorkPlaceID = employeePublisher.getListWorkPlaceID(employeeID, referenceDate);
		return listWorkPlaceID;
	}

	@Override
	public List<String> getWorkplaceListId(GeneralDate referenceDate, String employeeID, boolean referEmployee) {
		List<String> listWorkPlaceID = workplaceListPub.getWorkplaceListId(referenceDate, employeeID, referEmployee);
		return listWorkPlaceID;
	}

}
