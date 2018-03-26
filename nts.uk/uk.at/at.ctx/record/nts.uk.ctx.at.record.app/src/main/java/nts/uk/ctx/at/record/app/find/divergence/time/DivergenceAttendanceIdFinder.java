package nts.uk.ctx.at.record.app.find.divergence.time;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeRepository;

/**
 * The Class DivergenceAttendanceIdFinder.
 */
@Stateless
public class DivergenceAttendanceIdFinder {

	/** The div time repo. */
	@Inject
	DivergenceTimeRepository divTimeRepo;

}
