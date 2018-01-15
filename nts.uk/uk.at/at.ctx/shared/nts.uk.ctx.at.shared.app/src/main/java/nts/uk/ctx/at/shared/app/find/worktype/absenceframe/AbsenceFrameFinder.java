package nts.uk.ctx.at.shared.app.find.worktype.absenceframe;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrame;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrameRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TanLV
 *
 */
@Stateless
public class AbsenceFrameFinder {
	@Inject
	private AbsenceFrameRepository absenceFrameRepository;
	
	/**
	 * Find by company id.
	 *
	 * @return the list
	 */
	public List<AbsenceFrameDto> findAll() {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		return this.absenceFrameRepository.findAll(companyId).stream().map(c -> AbsenceFrameDto.fromDomain(c))
				.collect(Collectors.toList());
	}
	
	/**
	 * Find by frame no.
	 *
	 * @return the data
	 */
	public AbsenceFrameDto findAbsenceFrameByCode(int frameNo) {
		// user contexts
		String companyId = AppContexts.user().companyId();

		Optional<AbsenceFrame> data = this.absenceFrameRepository.findAbsenceFrameByCode(companyId, frameNo);
		
		if(data.isPresent()){
			return AbsenceFrameDto.fromDomain(data.get());
		}
		
		return null;
	}
}
