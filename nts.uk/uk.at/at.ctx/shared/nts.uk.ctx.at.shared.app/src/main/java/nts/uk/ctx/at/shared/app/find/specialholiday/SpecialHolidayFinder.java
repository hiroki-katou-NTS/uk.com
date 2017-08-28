package nts.uk.ctx.at.shared.app.find.specialholiday;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SpecialHolidayFinder {

	@Inject
	private SpecialHolidayRepository specialHolidayRepository;

	/**
	 * Find all Special Holiday by CompanyId
	 * 
	 * @return
	 */
	public List<SpecialHolidayDto> findAllSpecialHoliday() {
		String companyId = AppContexts.user().companyId();
		return specialHolidayRepository.findByCompanyId(companyId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}

	/**
	 * Convert to Database Type SpecialHolidayDto
	 * 
	 * @param specialHoliday
	 * @return
	 */
	private SpecialHolidayDto convertToDbType(SpecialHoliday specialHoliday) {
		SpecialHolidayDto specialHolidayDto = new SpecialHolidayDto();
		specialHolidayDto.setSpecialHolidayCode(specialHoliday.getSpecialHolidayCode().v());
		specialHolidayDto.setSpecialHolidayName(specialHoliday.getSpecialHolidayName().v());
		specialHolidayDto.setGrantPeriodicCls(specialHoliday.getGrantPeriodicCls().value);
		specialHolidayDto.setMemo(specialHoliday.getMemo().v());
		specialHolidayDto.setWorkTypeList(specialHoliday.getWorkTypeList());
		
		return specialHolidayDto;
	}
}
