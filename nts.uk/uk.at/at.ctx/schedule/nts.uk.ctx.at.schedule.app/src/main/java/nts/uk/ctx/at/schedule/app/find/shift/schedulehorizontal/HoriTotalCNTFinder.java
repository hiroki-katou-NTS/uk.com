package nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
/**
 * 
 * @author yennth
 *
 */

import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.repository.HoriTotalCategoryRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class HoriTotalCNTFinder {
	@Inject
	private HoriTotalCategoryRepository horiRep;
	
	public List<HoriTotalCNTSetDto> finder(){
		String companyId = AppContexts.user().companyId();
		return this.horiRep.findAllCNT(companyId)
							.stream()
							.map(x -> {
								return new HoriTotalCNTSetDto(companyId, x.getCategoryCode().toString(), x.getTotalItemNo(), x.getTotalTimeNo());
							}).collect(Collectors.toList());
	}
}
