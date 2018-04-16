package nts.uk.ctx.at.function.app.find.holidaysremaining;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.holidaysremaining.repository.SpecialHolidayRepository;

@Stateless
/**
 * 出力する特別休暇
 */
public class SpecialHolidayFinder {

	@Inject
	private SpecialHolidayRepository specialHolidayRepository;
	
	
}
