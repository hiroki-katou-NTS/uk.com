package nts.uk.ctx.sys.env.app.find.sysusagesetfinder;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.env.dom.useatr.SysUsageRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class SysUsageSetFinder {
	@Inject
	private SysUsageRepository sysRep;
	/**
	 * find a system usage setting item
	 * @param companyId
	 * @param companyCode
	 * @return
	 * author: Hoang Yen
	 */
	
	public Optional<SysUsageSetDto> finder(ParamFinder param){
		String contractCd = AppContexts.user().contractCode();
		return  this.sysRep.findUsageSet(param.companyId, param.companyCode, contractCd)
											.map(c -> {
												return new SysUsageSetDto(param.companyId, param.companyCode, contractCd,
														c.getPersonnelSystem().value,
														c.getEmploymentSys().value,
														c.getPayrollSys().value);
											});
	}
}
