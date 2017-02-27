package nts.uk.ctx.pr.core.app.find.rule.employement.processing.yearmonth;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.StandardDayRepository;

@Stateless
public class StandardDayFinder {

	@Inject
	private StandardDayRepository repository;
}
