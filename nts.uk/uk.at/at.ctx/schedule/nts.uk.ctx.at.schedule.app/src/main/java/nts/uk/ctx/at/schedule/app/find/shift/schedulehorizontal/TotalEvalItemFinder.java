package nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalItem;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.repository.HoriTotalCategoryRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class TotalEvalItemFinder {
	@Inject
	private HoriTotalCategoryRepository horiRep;
	/**
	 * convert from domain to dto
	 * @param yearServicePerSet
	 * @return
	 */
	private TotalEvalItemDto fromDomain(TotalEvalItem totalEvalItem){
		TotalEvalItemDto totalEvalItemDto = new TotalEvalItemDto();
		totalEvalItemDto.setTotalItemNo(totalEvalItemDto.getTotalItemNo());
		totalEvalItemDto.setTotalItemName(totalEvalItemDto.getTotalItemName());
		return totalEvalItemDto;
	}
	
	public List<TotalEvalItemDto> finder(){
		String companyId = AppContexts.user().companyId();
		return this.horiRep.findAllItem(companyId)
							.stream()
							.map(c -> fromDomain(c))
							.collect(Collectors.toList());
	}
}
