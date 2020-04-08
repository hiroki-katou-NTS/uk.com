package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDispInfoDto;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeService;
import nts.uk.ctx.at.request.dom.application.workchange.IWorkChangeRegisterService;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDispInfo;
import nts.uk.ctx.at.request.dom.application.workchange.output.ChangeWkTypeTimeOutput;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.IAppWorkChangeSetRepository;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.PredetemineTimeSettingDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppWorkChangeFinder {
	
	@Inject
	private IAppWorkChangeSetRepository appWRep;
	
	@Inject 
	private IWorkChangeRegisterService workChangeRegisterService;
	
	@Inject
	private AppWorkChangeService appWorkChangeService;
	
	public AppWorkChangeSetDto findByCom(){
		String companyId = AppContexts.user().companyId();
		Optional<AppWorkChangeSet> app = appWRep.findWorkChangeSetByID(companyId);
		if(app.isPresent()){
			return AppWorkChangeSetDto.fromDomain(app.get());
		}
		return null;
	}
	
	public boolean isTimeRequired(String workTypeCD){
		return workChangeRegisterService.isTimeRequired(workTypeCD);
	}
	
	public AppWorkChangeDispInfoDto getStartNew(AppWorkChangeParam param) {
		String companyID = AppContexts.user().companyId();
		List<GeneralDate> dateLst = param.dateLst.stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd")).collect(Collectors.toList());
		AppWorkChangeDispInfo appWorkChangeDispInfo = appWorkChangeService.getStartNew(companyID, param.empLst, dateLst);
		return AppWorkChangeDispInfoDto.fromDomain(appWorkChangeDispInfo);
	}
	
	public AppWorkChangeDispInfoDto changeAppDate(AppWorkChangeParam param) {
		String companyID = AppContexts.user().companyId();
		List<GeneralDate> dateLst = param.dateLst.stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd")).collect(Collectors.toList());
		AppWorkChangeDispInfo appWorkChangeDispInfo = appWorkChangeService
				.changeAppDate(companyID, dateLst, param.appWorkChangeDispInfoDto.toDomain());
		return AppWorkChangeDispInfoDto.fromDomain(appWorkChangeDispInfo);
	}
	
	public AppWorkChangeDispInfoDto changeWorkSelection(AppWorkChangeParam param) {
		AppWorkChangeDispInfoDto result = param.appWorkChangeDispInfoDto;
		String companyID = AppContexts.user().companyId();
		ChangeWkTypeTimeOutput changeWkTypeTimeOutput = appWorkChangeService.changeWorkTypeWorkTime(
				companyID, 
				result.workTypeCD, 
				Optional.of(result.workTimeCD), 
				result.appWorkChangeSet.toDomain());
		result.setupType = changeWkTypeTimeOutput.getSetupType().value;
		PredetemineTimeSettingDto predetemineTimeSettingDto = null;
		if(changeWkTypeTimeOutput.getOpPredetemineTimeSetting().isPresent()) {
			changeWkTypeTimeOutput.getOpPredetemineTimeSetting().get().saveToMemento(predetemineTimeSettingDto);
		}
		result.predetemineTimeSetting = predetemineTimeSettingDto;
		return result;
	}
}
