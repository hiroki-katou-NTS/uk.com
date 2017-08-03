package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessType;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypesRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 勤務種別
 *
 */
@Stateless
public class BusinessTypesFinder {

	@Inject
	private BusinessTypesRepository workTypeRepository;

	/**
	 * find All business type
	 * @return
	 */
	public List<BusinessTypeDto> findAll() {
		String companyId = AppContexts.user().companyId();
		return this.workTypeRepository.findAll(companyId).stream().map(item -> {
			return new BusinessTypeDto(item.getWorkTypeCode().v(), item.getWorkTypeName().v());
		}).collect(Collectors.toList());
	}
	public Optional<BusinessTypeDto> findBusinessType(String workTypeCode){
		String companyId = AppContexts.user().companyId();
		Optional<BusinessType> businessType = workTypeRepository.findBusinessType(companyId, workTypeCode);
		BusinessTypeDto aaa = new BusinessTypeDto(businessType.get().getWorkTypeCode().v(),businessType.get().getWorkTypeName().v());
		return Optional.of(aaa);
	}
}
