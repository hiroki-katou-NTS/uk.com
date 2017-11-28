package nts.uk.ctx.bs.employee.app.find.employee.mngdata;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;

@Stateless
public class EmployeeDataMngInfoFinder {
	@Inject
	EmployeeDataMngInfoRepository edMngFinder;
}
