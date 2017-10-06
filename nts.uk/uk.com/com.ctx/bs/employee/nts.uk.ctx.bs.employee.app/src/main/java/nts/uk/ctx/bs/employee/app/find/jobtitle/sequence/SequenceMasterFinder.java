package nts.uk.ctx.bs.employee.app.find.jobtitle.sequence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.app.command.jobtitle.sequence.dto.SequenceMasterDto;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMaster;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SequenceMasterFinder.
 */
@Stateless
public class SequenceMasterFinder {

	/** The repository. */
	@Inject
	private SequenceMasterRepository repository;

	/**
	 * Find max order.
	 *
	 * @return the short
	 */
	public short findMaxOrder() {
		return this.repository.findMaxOrder();
	}
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<SequenceMasterDto> findAll() {
		String companyId = AppContexts.user().companyId();
		List<SequenceMaster> result = this.repository.findByCompanyId(companyId);

		return result.stream()
				.map(sequence -> {
					SequenceMasterDto memento = new SequenceMasterDto();
					sequence.saveToMemento(memento);
					return memento;
				})
				.collect(Collectors.toList());
	}
	
	/**
	 * Find sequence by sequence code.
	 *
	 * @param sequenceCode the sequence code
	 * @return the sequence master dto
	 */
	public SequenceMasterDto findSequenceBySequenceCode(String sequenceCode) {
		String companyId = AppContexts.user().companyId();
		Optional<SequenceMaster> opResult = this.repository.findBySequenceCode(companyId, sequenceCode);
		
		if(opResult.isPresent()) {
			SequenceMasterDto memento = new SequenceMasterDto();
			opResult.get().saveToMemento(memento);
			return memento;
		}
		
		return null;
	}
}
