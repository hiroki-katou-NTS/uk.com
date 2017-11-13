package nts.uk.ctx.bs.employee.app.find.workplacedifferinfor;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.workplace.differinfor.DivWorkDifferInforRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author yennth
 *
 */
@Stateless
public class DivWorkPlaceDifferInforFinder {
	@Inject
	private DivWorkDifferInforRepository divRep;
	/**
	 * find a item
	 * @param companyId
	 * @param companyCode
	 * @return
	 */
	public Optional<DivWorkPlaceDifferInforDto> finder(String companyId, String companyCode){
		String contractCd = AppContexts.user().contractCode();
		return this.divRep.findDivWork(companyId, companyCode, contractCd)
							.map(c -> {
								return new DivWorkPlaceDifferInforDto(companyId, companyCode, contractCd,
																	c.getRegWorkDiv().value);
							});
	}
}
