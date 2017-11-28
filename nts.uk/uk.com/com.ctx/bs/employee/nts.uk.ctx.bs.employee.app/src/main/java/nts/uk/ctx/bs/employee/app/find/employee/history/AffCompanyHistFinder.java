package nts.uk.ctx.bs.employee.app.find.employee.history;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;

@Stateless
public class AffCompanyHistFinder {
	@Inject
	AffCompanyHistRepository achFinder;

}
