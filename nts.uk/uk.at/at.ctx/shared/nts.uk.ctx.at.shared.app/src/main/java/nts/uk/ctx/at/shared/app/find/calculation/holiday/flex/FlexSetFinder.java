package nts.uk.ctx.at.shared.app.find.calculation.holiday.flex;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.calculation.holiday.flex.FlexSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.flex.FlexSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class FlexSetFinder {

	@Inject
	private FlexSetRepository repository;

	public List<FlexSetDto> findAllFlexSet() {
		String companyId = AppContexts.user().companyId();
		return repository.findByCompanyId(companyId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}

	private FlexSetDto convertToDbType(FlexSet flexSet) {

		FlexSetDto flexSetDto = new FlexSetDto();
		flexSetDto.setMissCalcHd(flexSet.getMissCalcHd());
		flexSetDto.setPremiumCalcHd(flexSet.getPremiumCalcHd());
		flexSetDto.setMissCalcSubhd(flexSet.getMissCalcSubhd());
		flexSetDto.setPremiumCalcSubhd(flexSet.getPremiumCalcSubhd());

		return flexSetDto;
	}
}
