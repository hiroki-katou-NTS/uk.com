package nts.uk.ctx.at.request.app.find.setting.company.otrestappcommon;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class OvertimeRestAppCommonSetFinder {
	@Inject
	private OvertimeRestAppCommonSetRepository otResRep;
	
	public OvertimeRestAppCommonSetDto findByAppType(){
		String companyID = AppContexts.user().companyId();
		Optional<OvertimeRestAppCommonSetting> dto = otResRep.getOvertimeRestAppCommonSetting(companyID, 0);
		if(dto.isPresent()){
			return OvertimeRestAppCommonSetDto.convertToDto(dto.get());
		}
		return null;
	}
	
	public OvertimeRestAppCommonSetDto findByApp7(){
		String companyID = AppContexts.user().companyId();
		Optional<OvertimeRestAppCommonSetting> dto = otResRep.getOvertimeRestAppCommonSetting(companyID, 6);
		if(dto.isPresent()){
			return OvertimeRestAppCommonSetDto.convertToDto(dto.get());
		}
		return null;
	}
}
