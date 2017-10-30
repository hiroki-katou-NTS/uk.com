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
 * find hori total category data
 * @author yennth
 *
 */
@Stateless
public class HoriTotalCategoryFinder {
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
	 * convert from domain to dto
	 * @param list horitotalcntset
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
	 * convert from domain to dto
	 * @param totalEvalOrder
	 * @return
	 * author: HoangYen
	 */
	private TotalEvalOrderDto fromDomain(TotalEvalOrder totalEvalOrder){
		TotalEvalOrderDto totalEvalOrderDto = new TotalEvalOrderDto();
		totalEvalOrderDto.setCategoryCode(totalEvalOrder.getCategoryCode().v());
		totalEvalOrderDto.setTotalItemNo(totalEvalOrder.getTotalItemNo().v());
		totalEvalOrderDto.setDispOrder(totalEvalOrder.getDispOrder());
		totalEvalOrderDto.setHoriCalDaySet(fromDomainCalSet(totalEvalOrder.getHoriCalDaysSet()));
		totalEvalOrderDto.setCntSetls(fromDomainCnt(totalEvalOrder.getCntSetls()));
		return totalEvalOrderDto;
	}
	
	/**
	 * find all hori total category
	 * @return
	 */
	public List<HoriTotalCategoryDto> finder(){
		String companyId = AppContexts.user().companyId();
		return this.horiRep.findAllCate(companyId)
							.stream()
							.map(x -> {
								return new HoriTotalCategoryDto(companyId,
																x.getCategoryCode().v(),
																x.getCategoryName().v(),
																x.getMemo().v(),
																x.getTotalEvalOrders().stream().map(c -> fromDomain(c)).collect(Collectors.toList()));
							}).collect(Collectors.toList());
	}
}
