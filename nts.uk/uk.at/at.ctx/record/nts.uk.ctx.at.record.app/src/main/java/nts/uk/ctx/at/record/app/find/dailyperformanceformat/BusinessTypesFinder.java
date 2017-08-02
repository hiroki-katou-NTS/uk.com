package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.List;
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
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<BusinessType> workTypes = workTypeRepository.findAll(companyId);

		// process in UI
		// if (workTypes.isEmpty()) {
		// throw new BusinessException("Msg_242");
		// }

		List<BusinessTypeDto> workTypeDtos = workTypes.stream().map(item -> {
			return new BusinessTypeDto(item.getWorkTypeCode().v(), item.getWorkTypeName().v());
		}).collect(Collectors.toList());
		workTypeDtos.sort((e2, e1) -> Integer.parseInt(e1.getWorkTypeCode()) - Integer.parseInt(e2.getWorkTypeCode()));

		return workTypeDtos;
	}

}
