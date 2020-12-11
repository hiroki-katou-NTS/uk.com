package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

//import nts.arc.error.BusinessException;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeDto;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.BusinessType;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.repository.BusinessTypesRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author nampt 勤務種別
 *
 */
@Stateless
public class BusinessTypesFinder {

	@Inject
	private BusinessTypesRepository businessTypeRepository;

	/**
	 * find All business type
	 * @return
	 */
	public List<BusinessTypeDto> findAll() {
		String companyId = AppContexts.user().companyId();
		return this.businessTypeRepository.findAll(companyId).stream().map(item -> {
			return new BusinessTypeDto(item.getBusinessTypeCode().v(), item.getBusinessTypeName().v());
		}).collect(Collectors.toList());
	}
	/**
	 * find business type by business type code
	 * @param businessTypeCode
	 * @return
	 */
	public BusinessTypeDto findByCode(String businessTypeCode){
		String companyId = AppContexts.user().companyId();
		Optional<BusinessType> businessType = businessTypeRepository.findByCode(companyId, businessTypeCode);
		if(!businessType.isPresent()){
			return null;
		}
		BusinessTypeDto businessTypeDto = new BusinessTypeDto(businessType.get().getBusinessTypeCode().v(),businessType.get().getBusinessTypeName().v());
		return businessTypeDto;
	}
}
