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
			return new BusinessTypeDto(item.getBusinessTypeCode().v(), item.getBusinessTypeName().v());
		}).collect(Collectors.toList());
	}
	public Optional<BusinessTypeDto> findBusinessType(String businessTypeCode){
		String companyId = AppContexts.user().companyId();
		Optional<BusinessType> businessType = workTypeRepository.findBusinessType(companyId, businessTypeCode);
		BusinessTypeDto aaa = new BusinessTypeDto(businessType.get().getBusinessTypeCode().v(),businessType.get().getBusinessTypeName().v());
		return Optional.of(aaa);
	}
}
