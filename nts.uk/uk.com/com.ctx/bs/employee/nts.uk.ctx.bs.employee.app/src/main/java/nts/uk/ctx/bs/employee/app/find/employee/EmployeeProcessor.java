package nts.uk.ctx.bs.employee.app.find.employee;

import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregFinder;

public class EmployeeProcessor implements PeregFinder<Object, PeregQuery>{

	@Override
	public String targetCategoryCode() {
		return "CS00002";
	}

	@Override
	public Class<?> finderClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
