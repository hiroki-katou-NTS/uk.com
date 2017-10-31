/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.workfixed;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixed;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository;

/**
 * The Class WorkFixedFinder.
 */
@Stateless
public class WorkFixedFinder {

	/** The workfixed repository. */
	@Inject
	private WorkfixedRepository workfixedRepository;

	/**
	 * Find work fixed by wkp id and closure id.
	 *
	 * @param workPlaceId
	 *            the work place id
	 * @param closureId
	 *            the closure id
	 * @return the work fixed dto
	 */
	public WorkFixedFinderDto findWorkFixedByWkpIdAndClosureId(String workPlaceId, Integer closureId) {

		Optional<WorkFixed> workFixed = this.workfixedRepository.findByWorkPlaceIdAndClosureId(workPlaceId, closureId);
		WorkFixedFinderDto workFixedDto = new WorkFixedFinderDto();

		if (workFixed.isPresent()) {
			workFixed.get().saveToMemento(workFixedDto);
		}

		return workFixedDto;
	}
	
	public List<WorkFixedFinderDto> findWorkFixed(List<WorkFixedFinderDto> listDto) {
		
		return listDto.stream()
			.map(dto -> {
				Optional<WorkFixed> workFixed = this.workfixedRepository.findByWorkPlaceIdAndClosureId(dto.getWkpId(), dto.getClosureId());				
				if (workFixed.isPresent()) {
					workFixed.get().saveToMemento(dto);
				}				
				return dto;
			})
			.collect(Collectors.toList());
	}
}
