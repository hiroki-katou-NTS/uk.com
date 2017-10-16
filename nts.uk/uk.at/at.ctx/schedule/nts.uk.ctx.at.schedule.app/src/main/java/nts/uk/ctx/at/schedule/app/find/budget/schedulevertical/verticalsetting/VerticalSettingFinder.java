package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalSet;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tanlv
 *
 */

@Stateless
public class VerticalSettingFinder {
	@Inject
	private VerticalSettingRepository repository;
	
	/**
	 * Find by company id.
	 *
	 * @return the list
	 */
	public List<VerticalSettingDto> findAllVerticalCalSet() {
		// user contexts
		String companyId = AppContexts.user().companyId();

		return this.repository.findAllVerticalCalSet(companyId).stream().map(c -> VerticalSettingDto.fromDomain(c))
				.collect(Collectors.toList());
	}
	
	/**
	 * Find by Cd.
	 *
	 * @return the item
	 */
	public VerticalSettingDto getVerticalCalSetByCode(String verticalCalCd) {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		Optional<VerticalCalSet> data = this.repository.getVerticalCalSetByCode(companyId, verticalCalCd);
		
		if(data.isPresent()){
			return VerticalSettingDto.fromDomain(data.get());
		}
		
		return null;
	}
}