package nts.uk.ctx.link.smile.app.smilelink;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSetting;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SmileAcceptLinkFinder {
	@Inject
	private SmileCooperationAcceptanceSettingRepository acceptRep;
	
	public SmileAcceptLinkDto findAcceptForSmile() {
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		
		List<SmileCooperationAcceptanceSetting> listAcceptDom = acceptRep.get(contractCode, companyId);
		
		SmileAcceptLinkDto acceptLink = new SmileAcceptLinkDto(listAcceptDom.stream().map(x -> new SmileAcceptSettingDto(x.getCooperationAcceptance().value, 
				x.getCooperationAcceptanceClassification().value, 
				x.getCooperationAcceptanceConditions().isPresent() ? Optional.of(x.getCooperationAcceptanceConditions().get().v()) : Optional.empty()
				)).collect(Collectors.toList()));
		
		return acceptLink;
	}
}
