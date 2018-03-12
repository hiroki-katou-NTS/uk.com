package nts.uk.ctx.at.request.app.find.setting.company.applicationcommonsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.AppCommonSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.AppCommonSetRepository;
/**
 * 
 * @author yennth
 *
 */
@Stateless
public class AppCommonSetFinder {
	@Inject
	private AppCommonSetRepository appRep;
	public AppCommonSetDto findByCom(){
		Optional<AppCommonSet> appCom = appRep.find();
		if(appCom.isPresent()){
			return AppCommonSetDto.convertToDto(appCom.get());
		}
		return null;
	}
}
