package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethod;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethodRepository;
import nts.uk.shr.com.context.AppContexts;

// TODO: Auto-generated Javadoc
/**
 * The Class DivergenceReasonInputMethodFinder.
 */
@Stateless
public class DivergenceReasonInputMethodFinder {

	/** The div reason repo. */
	@Inject
	private DivergenceReasonInputMethodRepository divReasonRepo;

	/**
	 * Gets the all div time.
	 *
	 * @return the all div time
	 */
	List<DivergenceReasonInputMethodDto> getAllDivTime() {
		// Get company id

		String companyId = AppContexts.user().companyId();

		// Get divergence time list
		List<DivergenceReasonInputMethod> listDivTime = this.divReasonRepo.getAllDivTime(companyId);

		// Check list empty
		if (listDivTime.isEmpty()) {
			return Collections.emptyList();
		}
		// Convert domain to Dto
		return listDivTime.stream().map(e -> {
			DivergenceReasonInputMethodDto dto = new DivergenceReasonInputMethodDto();
			e.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	/**
	 * Gets the div time detail.
	 *
	 * @param divTimeNo
	 *            the div time no
	 * @return the div time detail
	 */
	DivergenceReasonInputMethodDto getDivTimeDetail(int divTimeNo) {
		// get company id
		String companyId = AppContexts.user().companyId();

		// get DivergenceReasonInputMethod info
		DivergenceReasonInputMethodDto divReasonInputDto = new DivergenceReasonInputMethodDto();
		return null;
	}

}
