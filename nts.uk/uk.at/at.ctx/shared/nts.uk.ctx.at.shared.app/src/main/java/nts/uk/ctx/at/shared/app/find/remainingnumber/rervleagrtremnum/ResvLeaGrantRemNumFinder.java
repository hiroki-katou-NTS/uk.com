package nts.uk.ctx.at.shared.app.find.remainingnumber.rervleagrtremnum;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSyEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSyEmploymentImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.app.command.vacation.setting.retentionyearly.dto.UpperLimitSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ResvLeaGrantRemNumFinder {

	@Inject
	private RervLeaGrantRemDataRepository repository;
	
	@Inject
	private SharedSyEmploymentAdapter syEmploymentAdapter;
	
	@Inject
	private RetentionYearlySettingRepository reYearSetRepo;
	
	@Inject
	private EmploymentSettingRepository emSetRepo;;

	public List<ResvLeaGrantRemNumDto> find(String employeeId) {
		List<ReserveLeaveGrantRemainingData> dataList = repository.find(employeeId, AppContexts.user().companyId());
		return dataList.stream().map(domain -> ResvLeaGrantRemNumDto.createFromDomain(domain))
				.collect(Collectors.toList());
	}
	
	public List<ResvLeaGrantRemNumDto> findNotExp(String employeeId) {
		List<ReserveLeaveGrantRemainingData> dataList = repository.findNotExp(employeeId, AppContexts.user().companyId());
		return dataList.stream().map(domain -> ResvLeaGrantRemNumDto.createFromDomain(domain))
				.collect(Collectors.toList());
	}
	
	public ResvLeaGrantRemNumDto getById(String id) {
		Optional<ReserveLeaveGrantRemainingData> data = repository.getById(id);
		if(data.isPresent())
			return data.map(x -> ResvLeaGrantRemNumDto.createFromDomain(x)).get();
		return null;
	}
	
	public GeneralDate generateDeadline(GeneralDate grantDate){
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		UpperLimitSetting upLimSet = getUpperLimitSetting(companyId, employeeId, grantDate);
		if(upLimSet.getRetentionYearsAmount().v() > 0){
			GeneralDate deadline = grantDate.addYears(upLimSet.getRetentionYearsAmount().v());
			return deadline.addDays(-1);
		}else{
			return grantDate;
		}
	}
	
	private UpperLimitSetting getUpperLimitSetting(String companyId, String employeeId, GeneralDate grantDate){
		UpperLimitSetting upLimSet = null;
		Optional<SharedSyEmploymentImport> syEmployment = syEmploymentAdapter.findByEmployeeId(companyId, employeeId, grantDate);
		if(!syEmployment.isPresent()){
			upLimSet = getUpLimSetEmpNone(companyId);
		}else{
			Optional<EmptYearlyRetentionSetting> emptYearlyRetSet = emSetRepo.find(companyId, syEmployment.get().getEmploymentCode());
			if(emptYearlyRetSet.isPresent()){
				if(emptYearlyRetSet.get().getManagementCategory() == ManageDistinct.YES){
					//upLimSet = emptYearlyRetSet.get().getUpperLimitSetting();
				}
				else{
					upLimSet = setDefaultLimSet();
				}
			}else{
				upLimSet = getUpLimSetEmpNone(companyId);
			}
		}
		return upLimSet;
	}
	
	private UpperLimitSetting getUpLimSetEmpNone(String companyId){
		Optional<RetentionYearlySetting> reYearSet = reYearSetRepo.findByCompanyId(companyId);
		if(reYearSet.isPresent())
		{
			return reYearSet.get().getUpperLimitSetting();
		}else{
			return setDefaultLimSet();
		}
	}
	
	private UpperLimitSetting setDefaultLimSet(){
		UpperLimitSettingDto dto = new UpperLimitSettingDto();
		dto.setMaxDaysCumulation(0);
		dto.setRetentionYearsAmount(0);
		return dto.toDomain();
	}
	
	
}
