/*
 * 
 */
package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelect;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DivergenceReasonSelectFinder.
 */
@Stateless
public class DivergenceReasonSelectFinder {

	/** The div reason select repo. */
	@Inject
	DivergenceReasonSelectRepository divReasonSelectRepo;

	/**
	 * Gets the all reason.
	 *
	 * @param divTimeNo the div time no
	 * @return the all reason
	 */
	public List<DivergenceReasonSelectDto> getAllReason(int divTimeNo) {
		// Get company id
		String companyId = AppContexts.user().companyId();

		// Get list divergence reason
		List<DivergenceReasonSelect> reasonList = divReasonSelectRepo.findAllReason(divTimeNo, companyId);

		// Check list empty
		if (reasonList.isEmpty()) {
			return Collections.emptyList();
		}

		// Convert domain to dto
		return reasonList.stream().map(e -> {
			DivergenceReasonSelectDto dto = new DivergenceReasonSelectDto();
			e.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	/**
	 * Gets the reason info.
	 *
	 * @param dto the dto
	 * @return the reason info
	 */
	public DivergenceReasonSelectDto getReasonInfo(DivergenceReasonSelectDto dto) {

		// Get company id
		String companyId = AppContexts.user().companyId();

		// Get divergence reason
		DivergenceReasonSelect reason = divReasonSelectRepo.findReasonInfo(dto.getDivergenceTimeNo(), companyId,
				dto.getDivergenceReasonCode());

		if (reason != null) {
			DivergenceReasonSelectDto reasonDto = new DivergenceReasonSelectDto();
			reason.saveToMemento(reasonDto);
			return reasonDto;
		}

		// convert to Dto
		DivergenceReasonSelectDto divReasonSelectDto = new DivergenceReasonSelectDto(dto.getDivergenceTimeNo(),
				reason.getDivergenceReasonCode().toString(), reason.getReason().toString(),
				reason.getReasonRequired().value);

		// Return
		return divReasonSelectDto;

	}
}
