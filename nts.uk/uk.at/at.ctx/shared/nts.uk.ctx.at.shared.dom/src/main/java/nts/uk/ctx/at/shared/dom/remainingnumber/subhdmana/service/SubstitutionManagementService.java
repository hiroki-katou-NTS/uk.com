package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SWkpHistImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysWorkplaceAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SubstitutionManagementService {
	
	@Inject
	private SysWorkplaceAdapter syWorkplaceAdapter;
	
	@Inject
	private ExtraHolidayManagementService extraHolidayManagementService;

	
	public SubstituteManagementOutput activationProcess(GeneralDate startDate, GeneralDate endDate){
		String employeeId = AppContexts.user().employeeId();
		GeneralDate baseDate = GeneralDate.today();
		Optional<SWkpHistImport> sWkpHistImport = syWorkplaceAdapter.findBySid(employeeId, baseDate);
		if (sWkpHistImport.isPresent()){
		ExtraHolidayManagementOutput extraHolidayManagementOutput = extraHolidayManagementService.dataExtractionProcessing(0, employeeId);
			return new SubstituteManagementOutput(sWkpHistImport.orElse(null), extraHolidayManagementOutput);
		} else{
			throw new BusinessException("Msg_504");
		}
	}
}
