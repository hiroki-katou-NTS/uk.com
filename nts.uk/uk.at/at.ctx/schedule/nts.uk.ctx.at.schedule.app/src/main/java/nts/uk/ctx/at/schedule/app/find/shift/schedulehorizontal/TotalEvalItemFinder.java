package nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalItem;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.repository.HoriTotalCategoryRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * find total eval item data
 * @author yennth
 *
 */
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
		totalEvalItemDto.setTotalItemNo(totalEvalItem.getTotalItemNo().v());
		totalEvalItemDto.setTotalItemName(totalEvalItem.getTotalItemName().v());
		return totalEvalItemDto;
	}
	/**
	 * find all total eval item data
	 * @return
	 */
	public List<TotalEvalItemDto> finder(){
		String companyId = AppContexts.user().companyId();
		List<TotalEvalItemDto> totalEvalItemDtos = horiRep.findAllItem(companyId)
				.stream()
				.map(c -> fromDomain(c))
				.collect(Collectors.toList());
 		return totalEvalItemDtos;
	}
}
