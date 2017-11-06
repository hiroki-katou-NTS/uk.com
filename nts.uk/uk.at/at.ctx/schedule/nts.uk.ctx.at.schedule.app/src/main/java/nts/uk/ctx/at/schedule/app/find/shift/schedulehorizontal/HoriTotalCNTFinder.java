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
/**
 * find hori total cnt set data
 * @author yennth
 *
 */
@Stateless
public class HoriTotalCNTFinder {
	@Inject
	private HoriTotalCategoryRepository horiRep;
	/**
	 *  find hori total cnt set list by categoryCode, totalItemNo
	 * @param param
	 * @return
	 */
	public List<HoriTotalCNTSetDto> finder(Param param){
		String companyId = AppContexts.user().companyId();
		return this.horiRep.findCNTSet(companyId, param.getCategoryCode(), param.getTotalItemNo())
							.stream()
							.map(x -> {
								return new HoriTotalCNTSetDto(companyId, param.getCategoryCode(), param.getTotalItemNo(), x.getTotalTimeNo());
							}).collect(Collectors.toList());
	}
	
	/**
	 *  find all hori total cnt set list 
	 * @param ParamExternalBudget
	 * @return
	 */
	public List<HoriTotalCNTSetDto> finderAll(){
		String companyId = AppContexts.user().companyId();
		return this.horiRep.findAllCNT(companyId)
							.stream()
							.map(x -> {
								return new HoriTotalCNTSetDto(companyId, 
																x.getCategoryCode(),
																x.getTotalItemNo(),
																x.getTotalTimeNo());
							}).collect(Collectors.toList());
	}
}
