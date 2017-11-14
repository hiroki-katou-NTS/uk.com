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

import org.apache.logging.log4j.util.Strings;

import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoImportedDto;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixed;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkFixedFinder.
 */
@Stateless
public class WorkFixedFinder {

	/** The workfixed repository. */
	@Inject
	private WorkfixedRepository workfixedRepository;

	/** The person info adapter. */
	@Inject
	private PersonInfoAdapter personInfoAdapter;

	/**
	 * Find work fixed by wkp id and closure id.
	 *
	 * @param workPlaceId the work place id
	 * @param closureId the closure id
	 * @param cid the cid
	 * @return the work fixed finder dto
	 */
	public WorkFixedFinderDto findWorkFixedByWkpIdAndClosureId(String workPlaceId, Integer closureId, String cid) {

		Optional<WorkFixed> workFixed = this.workfixedRepository.findByWorkPlaceIdAndClosureId(workPlaceId, closureId,
				cid);
		WorkFixedFinderDto workFixedDto = new WorkFixedFinderDto();

		if (workFixed.isPresent()) {
			workFixed.get().saveToMemento(workFixedDto);
		}

		return workFixedDto;
	}

	/**
	 * Find work fixed info.
	 *
	 * @param listDto the list dto
	 * @return the list
	 */
	public List<WorkFixedFinderDto> findWorkFixedInfo(List<WorkFixedFinderDto> listDto) {
		// Get company id
		String companyId = AppContexts.user().companyId();
		return listDto.stream().map(dto -> {
			Optional<WorkFixed> workFixed = this.workfixedRepository.findByWorkPlaceIdAndClosureId(dto.getWkpId(),
					dto.getClosureId(), companyId);
			if (workFixed.isPresent()) {
				workFixed.get().saveToMemento(dto);
			}
			if (Strings.isNotEmpty(dto.getConfirmPid())) {
				PersonInfoImportedDto personImportDto = this.personInfoAdapter.getPersonInfo(dto.getConfirmPid());			
				dto.setEmployeeName(personImportDto.getEmployeeName());
			}			
			
			return dto;
		}).collect(Collectors.toList());
	}
	
	/**
	 * Find current person name.
	 *
	 * @return the person info work fixed dto
	 */
	public PersonInfoWorkFixedDto findCurrentPersonName() {
		// Get Person Id
		String personId = AppContexts.user().personId();
		PersonInfoImportedDto personImportDto = this.personInfoAdapter.getPersonInfo(personId);			
		return PersonInfoWorkFixedDto.builder()
				.employeeId(personImportDto.getEmployeeId())
				.employeeName(personImportDto.getEmployeeName())
				.build();
	}
}
