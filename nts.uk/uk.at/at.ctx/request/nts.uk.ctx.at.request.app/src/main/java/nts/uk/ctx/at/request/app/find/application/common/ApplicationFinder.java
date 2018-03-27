package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationMetaDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationPeriodDto;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApplicationFinder {
	
	@Inject
	private ApplicationRepository_New applicationRepository;
	
	@Inject
	private DetailAppCommonSetService detailAppCommonSetService;
	
	public List<ApplicationMetaDto> getAppbyDate(ApplicationPeriodDto dto){
		String companyID = AppContexts.user().companyId();
		return this.applicationRepository.getApplicationIdByDate(companyID, dto.getStartDate(), dto.getEndDate())
				.stream().map(c -> { return new ApplicationMetaDto(c.getAppID(), c.getAppType().value, c.getAppDate()); })
				.collect(Collectors.toList());
	}
	
	public ApplicationMetaDto getAppByID(String appID){
		String companyID = AppContexts.user().companyId();
		return ApplicationMetaDto.fromDomain(detailAppCommonSetService.getDetailAppCommonSet(companyID, appID));
	}

}
