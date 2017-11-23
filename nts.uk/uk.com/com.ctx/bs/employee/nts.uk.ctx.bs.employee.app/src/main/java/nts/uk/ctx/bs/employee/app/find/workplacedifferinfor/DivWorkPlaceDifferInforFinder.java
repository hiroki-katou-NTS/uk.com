package nts.uk.ctx.bs.employee.app.find.workplacedifferinfor;

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
	public DivWorkPlaceDifferInforDto finder(ParamFinder param){
		String contractCd = AppContexts.user().contractCode();
		return this.divRep.findDivWork(param.getCompanyId())
							.map(c -> {
								return new DivWorkPlaceDifferInforDto(param.getCompanyId(),
																	c.getRegWorkDiv().value);
							}).orElse(null);
	}
}
