package nts.uk.ctx.at.shared.app.find.worktype.specialholidayframe;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrameRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TanLV
 *
 */
@Stateless
public class SpecialHolidayFrameFinder {
	@Inject
	private SpecialHolidayFrameRepository specialHolidayFrameRepository;
	
	/**
	 * Find by company id.
	 *
	 * @return the list
	 */
	public List<SpecialHolidayFrameDto> findAll() {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		return this.specialHolidayFrameRepository.findAll(companyId).stream().map(c -> SpecialHolidayFrameDto.fromDomain(c))
				.collect(Collectors.toList());
	}
	
	/**
	 * Find by company id. and 使用区分　=　true
	 *
	 * @return the list
	 */
	public List<SpecialHolidayFrameDto> findSpecialHolidayFrame() {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		return this.specialHolidayFrameRepository.findSpecialHolidayFrame(companyId).stream().map(c -> SpecialHolidayFrameDto.fromDomain(c))
				.collect(Collectors.toList());
	}
	
	/**
	 * Find by frame no.
	 *
	 * @return the data
	 */
	public SpecialHolidayFrameDto findHolidayFrameByCode(int frameNo) {
		// user contexts
		String companyId = AppContexts.user().companyId();

		Optional<SpecialHolidayFrame> data = this.specialHolidayFrameRepository.findHolidayFrameByCode(companyId, frameNo);
		
		if(data.isPresent()){
			return SpecialHolidayFrameDto.fromDomain(data.get());
		}
		
		return null;
	}
}
