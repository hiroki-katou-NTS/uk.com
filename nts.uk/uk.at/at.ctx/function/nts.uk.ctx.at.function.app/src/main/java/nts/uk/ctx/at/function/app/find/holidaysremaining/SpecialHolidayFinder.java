package nts.uk.ctx.at.function.app.find.holidaysremaining;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.holidaysremaining.repository.SpecialHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
 * 出力する特別休暇
 */
public class SpecialHolidayFinder {

	@Inject
	private SpecialHolidayRepository specialHolidayRepository;

	public List<SpecialHolidayDto> findAll() {
		return this.specialHolidayRepository.getProcessExecutionLogByCompanyId(AppContexts.user().companyId()).stream()
				.map(a -> {
					SpecialHolidayDto dto = SpecialHolidayDto.fromDomain(a);
					return dto;
				}).collect(Collectors.toList());
	}

}
