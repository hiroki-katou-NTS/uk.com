package nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;

/**
 * @author sang.nv
 *
 */
@Stateless
public class CompensatoryDayOffManaDataFinder {

	@Inject
	private ComDayOffManaDataRepository comDayOffRepo;

	public CompensatoryDayOffManaDataDto getCompensatoryByComDayOffID(String comDayOffID) {
		return CompensatoryDayOffManaDataDto
				.createFromDomain(comDayOffRepo.getCompensatoryByComDayOffID(comDayOffID).get());
	}
}
