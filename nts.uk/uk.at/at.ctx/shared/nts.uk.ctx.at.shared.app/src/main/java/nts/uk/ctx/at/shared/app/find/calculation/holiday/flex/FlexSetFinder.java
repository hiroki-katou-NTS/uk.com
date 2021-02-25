package nts.uk.ctx.at.shared.app.find.calculation.holiday.flex;

import java.util.List;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSetRepository;
import nts.uk.shr.com.context.AppContexts;


/**
 * @author phongtq 
 * The class Flex Set Finder
 */

@Stateless
public class FlexSetFinder {

	@Inject
	private FlexSetRepository repository;

	/**
	 * Find all Flex Set
	 * 
	 * @return
	 */
	public List<FlexSetDto> findAllFlexSet() {
		String companyId = AppContexts.user().companyId();
		return repository.findByCompanyId(companyId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}

	/**
	 * Convert to Database Flex Set
	 * 
	 * @param flexSet
	 * @return
	 */
	private FlexSetDto convertToDbType(FlexSet flexSet) {

		FlexSetDto flexSetDto = new FlexSetDto();
		flexSetDto.setMissCalcHd(flexSet.getMissCalcHd().value);
		flexSetDto.setPremiumCalcHd(flexSet.getPremiumCalcHd().value);
		flexSetDto.setMissCalcSubhd(flexSet.getMissCalcSubhd().value);
		flexSetDto.setPremiumCalcSubhd(flexSet.getPremiumCalcSubhd().value);
		flexSetDto.setFlexNonworkingDayCalc(flexSet.getFlexNonworkingDayCalc().value);
		flexSetDto.setFlexDeductTimeCalc(flexSet.getFlexDeductTimeCalc().value);

		return flexSetDto;
	}
}
