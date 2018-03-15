package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeRepository;

@Stateless
public class DivergenceAttendanceIdFinder {

	@Inject
	DivergenceTimeRepository divTimeRepo;
	
	
}
