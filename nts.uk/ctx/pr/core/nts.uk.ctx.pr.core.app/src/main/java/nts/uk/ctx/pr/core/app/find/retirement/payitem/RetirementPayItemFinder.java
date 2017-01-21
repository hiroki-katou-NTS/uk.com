package nts.uk.ctx.pr.core.app.find.retirement.payitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.retirement.payitem.dto.RetirementPayItemDto;
import nts.uk.ctx.pr.core.dom.retirement.payitem.RetirementPayItemRepository;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@RequestScoped
public class RetirementPayItemFinder {
	@Inject
	private RetirementPayItemRepository retirementPayItemRepository;
	
	public List<RetirementPayItemDto> findAll(){
		return this.retirementPayItemRepository.findAll()
				.stream()
				.map(x -> new RetirementPayItemDto(
						x.getCompanyCode().v(),
						x.getCategory().value,
						x.getItemCode().v(),
						x.getItemName().v(),
						x.getPrintName().v(),
						x.getEnglishName().v(),
						x.getFullName().v(),
						x.getMemo().v()))
				.collect(Collectors.toList());
	}
}
