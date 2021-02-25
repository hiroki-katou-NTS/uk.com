package nts.uk.ctx.at.shared.app.find.calculation.holiday.flex;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.InsufficientFlexHolidayMnt;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.InsufficientFlexHolidayMntRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Classing handling getting insufficient flex holiday management data from repository
 * @author HoangNDH
 *
 */
@Stateless
public class InsufficientFlexHolidayMntFinder {
	/** The insufficient flex holiday management repository */
	@Inject
	InsufficientFlexHolidayMntRepository repository;
	
	/**
	 * Find all insufficient flex holiday management
	 * 
	 * @return
	 */
	public List<InsufficientFlexHolidayMntDto> findAllInsufficientFlexHolidayMnt() {
		String companyId = AppContexts.user().companyId();
		List<InsufficientFlexHolidayMntDto> listInsuff = repository.findByCompanyId(companyId).stream().map(e -> {
				return convertToDbType(e);
			}).collect(Collectors.toList());
		return listInsuff;
	}
	
	private InsufficientFlexHolidayMntDto convertToDbType(InsufficientFlexHolidayMnt insuffFlex) {
		InsufficientFlexHolidayMntDto dto = new InsufficientFlexHolidayMntDto();
		dto.supplementableDays = insuffFlex.getSupplementableDays().v();
		return dto;
	}
}
