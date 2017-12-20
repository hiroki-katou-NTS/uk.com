/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.scherec.totaltimes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto.TotalTimesDetailDto;
import nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto.TotalTimesItemDto;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class TotalTimesFinder.
 */
@Stateless
public class TotalTimesFinder {

	/** The total times repo. */
	@Inject
	private TotalTimesRepository totalTimesRepo;

	/**
	 * Gets the all total times items.
	 *
	 * @return the all total times items
	 */
	public List<TotalTimesItemDto> getAllTotalTimesItems() {

		String companyId = AppContexts.user().companyId();

		List<TotalTimesItemDto> lst = this.totalTimesRepo.getAllTotalTimes(companyId).stream()
				.map(domain -> {
					TotalTimesItemDto itemDto = new TotalTimesItemDto();
					domain.saveToMemento(itemDto);
					return itemDto;
				}).collect(Collectors.toList());

		return lst;
	}

	/**
	 * Gets the total times details.
	 *
	 * @return the total times details
	 */
	public TotalTimesDetailDto getTotalTimesDetails(Integer totalCountNo) {
		String companyId = AppContexts.user().companyId();

		Optional<TotalTimes> optTotalTimes = this.totalTimesRepo.getTotalTimesDetail(companyId,
				totalCountNo);

		if (!optTotalTimes.isPresent()) {
			return null;
		}

		TotalTimesDetailDto dto = new TotalTimesDetailDto();

		optTotalTimes.get().saveToMemento(dto);

		return dto;
	}
}
