/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.workfixed;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.logging.log4j.util.Strings;

import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoImport;
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

		// Get WorkFixed info
		List<WorkFixed> listWorkFixed = this.workfixedRepository.findWorkFixed(companyId);
		Map<Entry<Integer, String>, WorkFixed> mapWorkFixed = listWorkFixed
				.stream()
				.collect(Collectors.toMap(
						item -> new ImmutablePair<Integer, String>(item.getClosureId(), item.getWkpId()),
						Function.identity()));

		// Get all Person info
		List<String> listPersonId = listWorkFixed.stream()
				.map(WorkFixed::getConfirmPid)
				.distinct()
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		Map<String, PersonInfoImport> listPerson = this.personInfoAdapter.getByListId(listPersonId)
				.stream()
				.collect(Collectors.toMap(PersonInfoImport::getPersonId, Function.identity()));

		return listDto.stream()
				.map(dto -> {
					WorkFixed workFixed = mapWorkFixed
							.get(new ImmutablePair<Integer, String>(dto.getClosureId(), dto.getWkpId()));
					if (workFixed == null) {
						return null;
					}
		
					// Get WorkFixed info
					if (!Strings.isEmpty(workFixed.getConfirmPid())) {
						// Get Person info
						PersonInfoImport person = listPerson.get(workFixed.getConfirmPid());
						if (person == null) {
							workFixed.saveToMemento(dto);
							return dto;
						}
						dto.setEmployeeName(person.getPersonName());
					}
		
					workFixed.saveToMemento(dto);
					return dto;
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	/**
	 * Find current person name.
	 *
	 * @return the person info work fixed dto
	 */
	public PersonInfoWorkFixedDto findCurrentPersonName() {
		// Get Person Id
		String personId = AppContexts.user().personId();
		PersonInfoImport personImportDto = this.personInfoAdapter.getPersonInfo(personId);
		if (personImportDto == null) {
			return null;
		}
		return PersonInfoWorkFixedDto.builder()
				.employeeId(personImportDto.getPersonId())
				.employeeName(personImportDto.getPersonName())
				.build();
	}
}
