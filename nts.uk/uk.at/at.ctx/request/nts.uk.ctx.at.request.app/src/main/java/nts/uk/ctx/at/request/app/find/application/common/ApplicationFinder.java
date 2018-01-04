package nts.uk.ctx.at.request.app.find.application.common;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationMetaDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationPeriodDto;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApplicationFinder {
	@Inject
	private ApplicationRepository appRepo;
	
	@Inject
	private ApplicationRepository_New applicationRepository;
	/**
	 * get all application
	 * @return
	 */
	public List<ApplicationDto> getAllApplication(){
		String companyID = AppContexts.user().companyId();
		return this.appRepo.getAllApplication(companyID).stream()
				.map(c->ApplicationDto.fromDomain(c))
				.collect(Collectors.toList());
	}
	/**
	 * get all application by code
	 * @return
	 */
	public ApplicationDto getAppById(String applicationID){
		String companyID = AppContexts.user().companyId();
		Optional<ApplicationDto> applicationDto = this.appRepo.getAppById(companyID, applicationID)
				.map(c->ApplicationDto.fromDomain(c));
		if(applicationDto.isPresent()) return applicationDto.get();
		else return null;
	}
	
	/**
	 * get all application by date
	 * @return
	 */
	public List<ApplicationDto> getAllAppByDate(GeneralDate applicationDate){
		String companyID = AppContexts.user().companyId();
		return this.appRepo.getAllAppByDate(companyID, applicationDate).stream()
				.map(c->ApplicationDto.fromDomain(c))
				.collect(Collectors.toList());
	}
	/**
	 * get all application by application type
	 * @return
	 */
	public List<ApplicationDto> getAllAppByAppType(int applicationType){
		String companyID = AppContexts.user().companyId();
		return this.appRepo.getAllAppByAppType(companyID, applicationType).stream()
				.map(c->ApplicationDto.fromDomain(c))
				.collect(Collectors.toList());
	}
	
	public List<ApplicationMetaDto> getAppbyDate(ApplicationPeriodDto dto){
		String companyID = AppContexts.user().companyId();
		return this.applicationRepository.getApplicationIdByDate(companyID, dto.getStartDate(), dto.getEndDate())
				.stream().map(c -> { return new ApplicationMetaDto(c.getAppID(), c.getAppType().value, c.getAppDate()); })
				.collect(Collectors.toList());
	}

}
