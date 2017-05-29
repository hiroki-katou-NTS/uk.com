package nts.uk.ctx.at.shared.app.vacation.setting.subst.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;

@Stateless
public class SubstVacationFinder {

	/** The com sv repository. */
	@Inject
	private ComSubstVacationRepository comSvRepository;

	/** The emp sv repository. */
	@Inject
	private EmpSubstVacationRepository empSvRepository;

}
