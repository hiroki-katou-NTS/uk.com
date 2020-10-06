package nts.uk.ctx.at.record.app.find.monthly.vtotalmethod;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.PayItemCountOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.PayItemCountOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * The Class PayItemCountOfMonthlyFinder.
 * @author HoangNDH
 */
@Stateless
public class PayItemCountOfMonthlyFinder {
	
	/** The repository. */
	@Inject
	PayItemCountOfMonthlyRepository repository;
	
	/** The wk type repo. */
	@Inject
	private WorkTypeRepository wkTypeRepo;
	
	/**
	 * Find setting.
	 *
	 * @return the pay item count of monthly dto
	 */
	public PayItemCountOfMonthlyDto findSetting() {
		String companyId = AppContexts.user().companyId();
		
		Optional<PayItemCountOfMonthly> optSetting = repository.find(companyId);
		if (optSetting.isPresent()) {
			PayItemCountOfMonthly setting = optSetting.get();
			PayItemCountOfMonthlyDto dto = new PayItemCountOfMonthlyDto();
			dto.setPayAttendanceDays(setting.getPayAttendanceDays().stream().map(p -> p.v()).collect(Collectors.toList()));
			dto.setPayAbsenceDays(setting.getPayAbsenceDays().stream().map(p -> p.v()).collect(Collectors.toList()));
			return dto;
		}
		return null;
	}
	
	/**
	 * Find work type.
	 *
	 * @param workTypeCode the work type code
	 * @return the work type
	 */
	public WorkTypeDto findWorkType(String workTypeCode) {
		String companyId = AppContexts.user().companyId();
		
		Optional<WorkType> wkTypeOpt = wkTypeRepo.findByPK(companyId, workTypeCode);
		
		if (wkTypeOpt.isPresent()) {
			WorkType workType = wkTypeOpt.get();
			WorkTypeDto dto = new WorkTypeDto();
			dto.setWorkTypeCode(workType.getWorkTypeCode().v());
			dto.setWorkTypeName(workType.getName().v());
			return dto;
		}
		return null;
	}
}
