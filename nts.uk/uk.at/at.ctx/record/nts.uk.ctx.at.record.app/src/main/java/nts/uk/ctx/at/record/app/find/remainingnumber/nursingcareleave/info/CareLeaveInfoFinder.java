package nts.uk.ctx.at.record.app.find.remainingnumber.nursingcareleave.info;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info.NursCareLevRemainInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;

@Stateless
public class CareLeaveInfoFinder {
	
	@Inject
	private NursCareLevRemainInfoRepository repo;
	
	public CareLeaveInfoDto getCareLeaveRemaining(String empId){
		Optional<NursingCareLeaveRemainingInfo> domainOpt = repo.getCareByEmpId(empId);
		if(!domainOpt.isPresent()) return null;
		NursingCareLeaveRemainingInfo domain = domainOpt.get();
		return new CareLeaveInfoDto(domain.getSId(), domain.isUseClassification(), domain.getUpperlimitSetting().value, 
				domain.getMaxDayForThisFiscalYear().isPresent() ? domain.getMaxDayForThisFiscalYear().get() : null, 
						domain.getMaxDayForNextFiscalYear().isPresent() ? domain.getMaxDayForNextFiscalYear().get() : null);
	}
	
	public CareLeaveInfoDto getChildCareLeaveRemaining(String empId){
		Optional<NursingCareLeaveRemainingInfo> domainOpt = repo.getChildCareByEmpId(empId);
		if(!domainOpt.isPresent()) return null;
		NursingCareLeaveRemainingInfo domain = domainOpt.get();
		return new CareLeaveInfoDto(domain.getSId(), domain.isUseClassification(), domain.getUpperlimitSetting().value, 
				domain.getMaxDayForThisFiscalYear().isPresent() ? domain.getMaxDayForThisFiscalYear().get() : null, 
						domain.getMaxDayForNextFiscalYear().isPresent() ? domain.getMaxDayForNextFiscalYear().get() : null);
	}
}
