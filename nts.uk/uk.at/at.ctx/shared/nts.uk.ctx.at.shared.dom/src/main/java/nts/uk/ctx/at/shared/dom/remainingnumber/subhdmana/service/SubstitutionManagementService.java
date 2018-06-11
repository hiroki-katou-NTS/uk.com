package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service;

import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SWkpHistImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
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
		ExtraHolidayManagementOutput extraHolidayManagementOutput = extraHolidayManagementService.dataExtractionProcessing(0, employeeId, startDate, endDate);
		
		return new SubstituteManagementOutput(sWkpHistImport.orElse(null), extraHolidayManagementOutput);
	}
}
