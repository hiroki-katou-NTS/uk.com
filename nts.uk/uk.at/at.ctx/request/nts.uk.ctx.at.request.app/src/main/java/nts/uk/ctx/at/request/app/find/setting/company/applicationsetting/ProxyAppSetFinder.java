package nts.uk.ctx.at.request.app.find.setting.company.applicationsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ProxyAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ProxyAppSetRepository;

@Stateless
public class ProxyAppSetFinder {
	@Inject
	private ProxyAppSetRepository proxyRep;
	
	public ProxyAppSetDto convertToDto(ProxyAppSet domain){
		return new ProxyAppSetDto(domain.getCompanyId(), domain.getAppType().value);
	}
	
	public List<ProxyAppSetDto> findAll(){
		List<ProxyAppSet> proxyList = proxyRep.getAllProxy();
		List<ProxyAppSetDto> dtoList = new ArrayList<>();
		for(ProxyAppSet item: proxyList){
			dtoList.add(convertToDto(item));
		}
		return dtoList;
	}
	
	public ProxyAppSetDto findByApp(int appType){
		Optional<ProxyAppSet> proxy = proxyRep.getProxy(appType);
		if(proxy.isPresent()){
			return ProxyAppSetDto.convertToDto(proxy.get());
		}
		return null;
	}
}
