package nts.uk.ctx.at.function.app.find.annualworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.annualworkschedule.repository.CalcFormulaItemRepository;

@Stateless
/**
* 項目の算出式
*/
public class CalcFormulaItemFinder
{
	@Inject
	private CalcFormulaItemRepository finder;

	public List<CalcFormulaItemDto> getAllCalcFormulaItem(){
		return finder.getAllCalcFormulaItem().stream().map(item -> CalcFormulaItemDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
