package nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriCalDaysSet;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriTotalCNTSet;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalOrder;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.repository.HoriTotalCategoryRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * find total eval order data
 * @author yennth
 *
 */
@Stateless
public class TotalEvalOrderFinder {
	@Inject
	private HoriTotalCategoryRepository horiRep;
	/**
	 * convert from domain to dto
	 * @param horiCalDaysSet
	 * @return
	 * author: HoangYen
	 */
	private HoriCalDaySetDto fromDomainCalSet(HoriCalDaysSet horiCalDaysSet){
			HoriCalDaySetDto horiCalDaySetDto = new HoriCalDaySetDto();
			if(horiCalDaysSet == null){
				horiCalDaySetDto = null;
				return horiCalDaySetDto;
			}
			horiCalDaySetDto.setCategoryCode(horiCalDaysSet.getCategoryCode().v());
			horiCalDaySetDto.setHalfDay(horiCalDaysSet.getHalfDay().value);
			horiCalDaySetDto.setYearHd(horiCalDaysSet.getYearHd().value);
			horiCalDaySetDto.setSpecialHoliday(horiCalDaysSet.getSpecialHoliday().value);
			horiCalDaySetDto.setHeavyHd(horiCalDaysSet.getHeavyHd().value);
		return horiCalDaySetDto;
	}
	
	/**
	 * convert list HoriTotalCNTSet from domain to dto
	 * @param list HoriTotalCNTSet
	 * @return
	 * author: HoangYen
	 */
	private List<HoriTotalCNTSetDto> fromDomainCnt(List<HoriTotalCNTSet> lstHoriTotalCNTSet){
		List<HoriTotalCNTSetDto> lst = new ArrayList<>(); 
		HoriTotalCNTSetDto horiTotalCNTSetDto = new HoriTotalCNTSetDto();
		for(HoriTotalCNTSet item: lstHoriTotalCNTSet){
			horiTotalCNTSetDto.setCategoryCode(item.getCategoryCode());
			horiTotalCNTSetDto.setTotalItemNo(item.getTotalItemNo());
			horiTotalCNTSetDto.setTotalTimeNo(item.getTotalTimeNo());
			lst.add(horiTotalCNTSetDto);
			horiTotalCNTSetDto = new HoriTotalCNTSetDto();
		}
		return lst;
	}
	
	
	/**
	 * convert from Total Eval Order domain to Total Eval Order dto
	 * @param totalEvalOrder
	 * @return
	 */
	private TotalEvalOrderDto fromDomain(TotalEvalOrder totalEvalOrder){
		TotalEvalOrderDto totalEvalOrderDto = new TotalEvalOrderDto();
		totalEvalOrderDto.setCategoryCode(totalEvalOrderDto.getCategoryCode());
		totalEvalOrderDto.setTotalItemNo(totalEvalOrderDto.getTotalItemNo());
		totalEvalOrderDto.setDispOrder(totalEvalOrderDto.getDispOrder());
		totalEvalOrderDto.setHoriCalDaySet(fromDomainCalSet(totalEvalOrder.getHoriCalDaysSet()));
		totalEvalOrderDto.setCntSetls(fromDomainCnt(totalEvalOrder.getCntSetls()));
		return totalEvalOrderDto;
	}
	
	/**
	 * find all total eval order
	 * @param categoryCode
	 * @param totalItemNo
	 * @return
	 */
	public List<TotalEvalOrderDto> finder(String categoryCode, int totalItemNo){
		String companyId = AppContexts.user().companyId();
		return this.horiRep.findOrder(companyId, categoryCode, totalItemNo)
							.stream()
							.map(x -> fromDomain(x))
							.collect(Collectors.toList());
	}
}
