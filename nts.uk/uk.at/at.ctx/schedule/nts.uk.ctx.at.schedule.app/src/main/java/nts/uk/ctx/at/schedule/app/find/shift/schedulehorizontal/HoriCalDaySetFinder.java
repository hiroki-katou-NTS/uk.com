package nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
/**
 * 
 * @author yennth
 *
 */
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.repository.HoriTotalCategoryRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * find hori cal days set data 
 * @author yennth
 *
 */
@Stateless
public class HoriCalDaySetFinder {
	@Inject
	private HoriTotalCategoryRepository horiRep;
	/**
	 *  find all hori cal days set data
	 * @return
	 */
	public List<HoriCalDaySetDto> finder(){
		String companyId = AppContexts.user().companyId();
		return this.horiRep.findAllCal(companyId)
							.stream()
							.map(x -> {
								return new HoriCalDaySetDto(companyId,
															x.getCategoryCode().v(),
															x.getTotalItemNo().v(),
															x.getHalfDay().value,
															x.getYearHd().value,
															x.getSpecialHoliday().value,
															x.getHeavyHd().value);
							}).collect(Collectors.toList());
	}
}
