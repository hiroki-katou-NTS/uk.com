package nts.uk.ctx.at.record.dom.remainingnumber.paymana.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SWkpHistImport;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SysWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SysEmploymentHisAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SubstitutionManagementService {
	
	@Inject
	private SysWorkplaceAdapter syWorkplaceAdapter;
	
	@Inject
	private SysEmploymentHisAdapter sysEmploymentHisAdapter;
	
	public void activationProcess(String employeeId){
		GeneralDate baseDate = GeneralDate.today();
		String companyId = AppContexts.user().companyId();
		Optional<SWkpHistImport> sWkpHistImport = syWorkplaceAdapter.findBySid(employeeId, baseDate);
		if (sWkpHistImport.isPresent()){
			
//			Optional<SEmpHistoryImport> sEmpHistoryImport = sysEmploymentHisAdapter.findSEmpHistBySid(companyId, employeeId, baseDate);
			
		}
	}
}
