package nts.uk.ctx.hr.develop.app.databeforereflecting.find;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.hr.develop.dom.databeforereflecting.RetirementInformation;
import nts.uk.ctx.hr.develop.dom.databeforereflecting.service.RetirementInformationService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckStatusRegistration {
	
	@Inject
	private RetirementInformationService retirementInfoService;
	

	public Boolean CheckStatusRegistration(String sid){
		
		String cid = AppContexts.user().companyId();
		Integer workId = 1;
		List<String> listPid =  Arrays.asList(sid);
		Optional<Boolean> includReflected = Optional.of(true);
		Optional<String> sortByColumnName = Optional.empty();
		Optional<String> orderType = Optional.empty();

		List<RetirementInformation> listRetirementInfo = retirementInfoService.getRetirementInfo(cid, workId, listPid,
				includReflected, sortByColumnName, orderType);
		
		if (!listRetirementInfo.isEmpty()) {
			throw new BusinessException("MsgJ_JCM007_5");
		}
		
		return true;
	}
}
