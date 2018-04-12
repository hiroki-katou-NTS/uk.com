package nts.uk.ctx.at.function.app.find.holidaysremaining;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.holidaysremaining.SpecialHoliday;

@Stateless
/**
 * 出力する特別休暇
 */
public class SpecialHolidayFinder {

	@Inject
	private SpecialHoliday specialHolidayFinder;

}
