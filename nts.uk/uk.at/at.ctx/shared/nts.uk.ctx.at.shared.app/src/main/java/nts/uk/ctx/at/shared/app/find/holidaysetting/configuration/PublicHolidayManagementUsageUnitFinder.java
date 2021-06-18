package nts.uk.ctx.at.shared.app.find.holidaysetting.configuration;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementUsageUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementUsageUnitRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PublicHolidayManagementUsageUnitFinder.
 */
@Stateless
public class PublicHolidayManagementUsageUnitFinder {
	
	@Inject
	private PublicHolidayManagementUsageUnitRepository repository;
	
	/**
	 * Find data.
	 *
	 * @return the public holiday management usage unit find dto
	 */
	public PublicHolidayManagementUsageUnitFindDto findData(){
		
		String companyId = AppContexts.user().companyId();
		
		Optional<PublicHolidayManagementUsageUnit> opt = this.repository.get(companyId);
		
		PublicHolidayManagementUsageUnitFindDto dto = new PublicHolidayManagementUsageUnitFindDto();
		
		if(opt.isPresent()){
			dto.setIsManageEmployeePublicHd(opt.get().getIsManageEmployeePublicHd());
			dto.setIsManageEmpPublicHd(opt.get().getIsManageEmpPublicHd());
			dto.setIsManageWkpPublicHd(opt.get().getIsManageWkpPublicHd());
			return dto;
		}
		return dto;
	}
}
